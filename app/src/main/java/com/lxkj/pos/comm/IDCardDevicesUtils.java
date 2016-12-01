package com.lxkj.pos.comm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import com.huayu.io.SerialPort;
import com.lxkj.pos.CommActivity;
import com.lxkj.pos.bean.SerialPortSendData;
import com.lxkj.pos.dao.IReciverListener;
import com.lxkj.pos.utils.StringUtils;
/**
 * @author levy
 * @date 2016-11-22 18:05:23
 * @ClassName: IDCardDevicesUtils
 * @Description:  身份证识别 硬件调用
 */
public class IDCardDevicesUtils {
	private static final String TAG = "IDCardDevicesUtils";
	//	private byte[] ReadBuff = new byte[1024];
	//    public static final byte START = (byte)0x86;    //开始
	//    public static final byte END = 0x16;      //结束
	//    public static final byte CMDA_CHECKDATA= (byte)0x81;          //有数据被读到
	//    public static final byte CMDQ_CHECKDATA = (byte)0x11;          //有数据被读到
	//    public static final byte CMDQ_ALL = (byte)0x20;          //读所有文本数据信息
	//    public static final byte CMDA_ALL = (byte)0x90;          //读所有文本数据信息应答
	//	private static final int TimeOut = 0;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private SerialPort mSerialPort;
	private static Context context;
	private boolean isReaddata=true;

	/**
	 * @author levy
	 * @creation 2016-11-22 18:10:44
	 * @instruction 读取串口的线程
	 */
	private class ReadThread extends Thread {
		private IReciverListener listener;
		private SerialPortSendData sendData;
		private boolean isappend;
		public ReadThread(SerialPortSendData sendData, IReciverListener listener) {
			this.listener = listener;
			this.sendData = sendData;
			isReaddata=true;
			isappend=true;
		}
		@Override
		public void run() {
			super.run();
			while (isReaddata) {
				try {
					byte[] buffer = new byte[1024];
					if (mInputStream == null){
						showDialog("mInputStream == null");
						return;
					}
					/*读取身份证文本信息的响应*/
					if ("90".equals(sendData.CMD)) {
						int size = 0;
						int readlength=0;
						StringBuffer sb90=new StringBuffer();
						///读取3次
						while (isappend) {
							size = mInputStream.read(buffer);
							if (size > 0) {
								String str = StringUtils.bytesToHexString(buffer, size).trim().toLowerCase();
								sb90.append(str);
								readlength++;
								if (str.substring(str.length()-2, str.length()).contains("16")||readlength==3) {
									isappend=false;
								}
								Thread.sleep(100);
							}else{
								//读取数据错误
								final String data = sb90.toString();
								Log.i(TAG,"读取数据错误");
								sb90=null;
								deInitComm();
								isReaddata=false;
								if(null == context){
									return;
								}
								((CommActivity) context).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										listener.onFail(data);
									}
								});
								return;
							}
						}
						Log.i(TAG,"读到信息为："+sb90);
						if(sb90.toString().substring(0, 12).contains(sendData.CMD)){//根据命令字
							Log.i(TAG,"读取响应90H成功！响应信息为："+sb90);
							final String data = sb90.toString();
							sb90=null;
							deInitComm();
							isReaddata=false;
							if(null == context){
								return;
							}
							((CommActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									listener.onReceived(data);
								}
							});
							return;
						}else{
							//读取数据错误
							Log.i(TAG,"错误的响应,响应信息为："+sb90);
							final String data = sb90.toString();
							sb90=null;
							deInitComm();
							isReaddata=false;
							if(null == context){
								return;
							}
							((CommActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									listener.onFail(data);
								}
							});
							return;
						}
					/*检测是否有身份证信息的响应*/
					}else if("81".equals(sendData.CMD)){
						int size=0;
						size = mInputStream.read(buffer);
						if (size > 0) { // 读取数据 数据c
							String sb81 = StringUtils.bytesToHexString(buffer, size).trim().toLowerCase();
							Log.i(TAG,"读到信息为："+sb81);
							if(sb81.toString().substring(0, 12).contains(sendData.CMD)&&//根据命令字
									sb81.toString().length()>=(sendData.checkinfoCount)){//根据大小
								//根据响应数据判断读到的数据
								//响应81H
								Log.i(TAG,"读取响应81H成功!响应信息为："+sb81);
								final String data = sb81.toString();
								sb81=null;
								isReaddata=false;
								if(null == context){
									return;
								}
								((CommActivity) context).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										listener.onReceived(data);
									}
								});
								return;
							}else{
								Log.i(TAG,"错误的响应,响应信息为："+sb81);
								deInitComm();
								final String data = sb81.toString();
								deInitComm();
								isReaddata=false;
								if(null == context){
									return;
								}
								((CommActivity) context).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										listener.onFail(data);
									}
								});
								return;
							}
						}else{
							Log.i(TAG,"无响应");
							deInitComm();
							final String data ="无响应";
							deInitComm();
							isReaddata=false;
							if(null == context){
								return;
							}
							((CommActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									listener.onFail(data);
								}
							});
							return;
						}
					/*清空本次记录数据的响应*/
					}else if("82".equals(sendData.CMD)){
						int size=0;
						size = mInputStream.read(buffer);
						if (size > 0) { // 读取数据 数据c
							String sb82 = StringUtils.bytesToHexString(buffer, size).trim().toLowerCase();
							Log.i(TAG,"读到信息为："+sb82);
							if(sb82.toString().substring(0, 12).contains(sendData.CMD)&&//根据命令字
									sb82.toString().length()>=(sendData.checkinfoCount)){//根据大小
								//响应82H
								Log.i(TAG,"读取响应82H成功!响应信息为："+sb82);
								deInitComm();
								final String data = sb82.toString();
								sb82=null;
								isReaddata=false;
								if(null == context){
									return;
								}
								((CommActivity) context).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										listener.onReceived(data);
									}
								});
								return;
							}else{
								Log.i(TAG,"错误的响应,响应信息为："+sb82);
								deInitComm();
								final String data = sb82.toString();
								deInitComm();
								isReaddata=false;
								if(null == context){
									return;
								}
								((CommActivity) context).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										listener.onFail(data);
									}
								});
								return;
							}
						}
					}else{
						Log.i(TAG,"读取错误的响应");
					}
				} catch (Exception e) {
					Log.i(TAG, "读取响应异常："+e);
					listener.onErr(e);
					return;
				}
			}
		}
	}

	/**
	 * close comm port
	 * 关闭端口和输入/输出流
	 */
	public void deInitComm() {
		// kill thread
		isReaddata = false;

		if (mOutputStream != null) {
			try {
				mOutputStream.close();
			} catch (java.io.IOException ioe) {
			}

			mOutputStream = null;
		}
		if (mInputStream != null) {
			try {
				mInputStream.close();
			} catch (java.io.IOException ioe) {
			}
			mInputStream = null;
		}

		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}

	/**
	 * 发送数据
	 *
	 * @param context context
	 * @param sendData 发送数据打包，
	 * @param listener 读取串口数据的回调
	 */
	public void toSend(Context context, SerialPortSendData sendData,
					   IReciverListener listener) {
		this.context = context;
		if ("".equals(sendData.path) || "/dev/tty".equals(sendData.path)) {
			Log.i(TAG, "设备地址不能为空");
			return;
			// devStr = "/dev/ttyS1";
		}
		try {
			//打开之前，先释放资源
			deInitComm();
			//打开串口获得输入、输出
			OpenCommport(sendData.path, sendData.baudRate);

			/**
			 * 因此机具上电后，不许任何命令便可以直接读取身份证，所以当commandStr命令为空时，则不需写命令，直接读取81H命令。
			 * 根据读取数据判断是否为81H，接着在成功回调里往串口写11H应答命令*/
			// 上面是获取设置而已 下面这个才是发送指令
			if (sendData.commandStr!=null) {
				//写串口命令
				writeCommandStr(sendData.commandStr);
			}
			/*创建一个读取响应的Thread*/
			mReadThread = new ReadThread(sendData, listener);
			mReadThread.start();
		} catch (SecurityException e) {
			Log.i(TAG,"SecurityException"+e);
			// DisplayError(R.string.error_security);
		} catch (IOException e) {
			Log.i(TAG,"IOException"+e);
			// DisplayError(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			Log.i(TAG,"InvalidParameterException"+e);
			// DisplayError(R.string.error_configuration);
		}
	}
	/**
	 * 写串口命令
	 * @param commandStr 命令字符串
	 * @throws IOException
	 */
	public void  writeCommandStr(String commandStr) throws IOException{
		byte[] text = StringUtils.hexStringToBytes(commandStr);
		mOutputStream.write(text);
		Log.i(TAG,"写串口命令："+commandStr+"转byte："+text);

	}
	/**
	 * 获取到串口通信的一个实例，拿到输入输出流
	 * @param path 串口地址
	 * @param baudrate 波特率
	 * @throws SecurityException
	 * @throws IOException
	 * @throws InvalidParameterException
	 */
	public void OpenCommport(String path, int baudrate)
			throws SecurityException, IOException, InvalidParameterException {
		/* Check parameters */
		if ((path.length() == 0) || (baudrate == -1)) {
			throw new InvalidParameterException();
		}
		/* Open the serial port */
		mSerialPort = new SerialPort(new File(path), baudrate,0,context );// 打开这个串口
		mOutputStream = mSerialPort.getOutputStream();
		mInputStream = mSerialPort.getInputStream();
		if (mOutputStream!=null||mInputStream!=null) {
			Log.i(TAG, "打开串口成功");
		}

	}
	private static void showDialog(String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(str)
				.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {


					}
				}).setCancelable(false).create().show();
	}


}