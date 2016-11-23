package com.lxkj.administrator.pos.comm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lxkj.administrator.pos.MainActivity;
import com.lxkj.administrator.pos.bean.SerialPortSendData;
import com.lxkj.administrator.pos.dao.IReciverListener;
import com.lxkj.administrator.pos.utils.StringUtils;

/**

 */
public class IDCardDevicesUtils {
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private SerialPort mSerialPort;
	private Context context;
	private boolean isReaddata=true;

	/**
	 * @author levy
	 * @creation 2016-11-22 18:10:44
	 * @instruction 读取串口的线程
	 */
	private class ReadThread extends Thread {
		private IReciverListener listener;
		private SerialPortSendData sendData;
		public ReadThread(SerialPortSendData sendData, IReciverListener listener) {
			this.listener = listener;
			this.sendData = sendData;
			isReaddata=true;
		}
		@Override
		public void run() {
			StringBuffer sb = new StringBuffer();
			super.run();
			while (isReaddata) {
				int size;
				try {
					byte[] buffer = new byte[2048];
					if (mInputStream == null){
						return;}
					size = mInputStream.read(buffer);
					if (size > 0) { // 读取数据 数据c
						String str = StringUtils.bytesToHexString(buffer, size).trim().toLowerCase();
						sb.append(str);
						if(sendData.numberStr.equals(sb.toString().substring(6, 8))&&
								sb.toString().length()>=(2*(sendData.numberLength))){//根据大小
							Log.i("---读取响应96H,身份证文本信息----","读取响应信息为："+sb);
							//身份证身份证文本信息
							final String data = sb.toString();
							deInitComm();
							isReaddata=false;
							if(null == context){
								return;
							}
							((MainActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {

									listener.onReceived(data);
								}
							});
							return;
						}else if(sendData.checkinfoStr.equals(sb.toString().substring(6, 8))&&
								sb.toString().length()<=(2*(sendData.checkinfoCount))){
							//根据响应数据判断读到的数据
							//响应81H
							Log.i("---读取响应81H成功----","读取响应信息为："+sb);
							final String data = sb.toString();
							isReaddata=false;
							if(null == context){
								return;
							}
							((MainActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {

									listener.onReceived(data);
								}
							});
							return;
						}else{
							Log.i("---错误的响应----","读取响应信息为："+sb);
							deInitComm();
							final String data = sb.toString();
							isReaddata=false;
							if (null == context){
								return;
							}
							((MainActivity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									listener.onFail(data);
								}
							});
							return;

						}
					}else{
						deInitComm();
						final String data = sb.toString();
						isReaddata=false;
						if (null == context){
							return;
						}
						((MainActivity) context).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								listener.onFail(data);
							}
						});
					}
				} catch (Exception e) {
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
	 * @param context
	 * @param sendData
	 * @param listener
	 */
	public void toSend(Context context, SerialPortSendData sendData,
					   IReciverListener listener) {
		this.context = context;
		if ("".equals(sendData.path) || "/dev/tty".equals(sendData.path)) {
			Toast.makeText(context, "设备地址不能为空", Toast.LENGTH_SHORT).show();
			return;
			// devStr = "/dev/ttyS1";
		}
		try {
			//打开之前，先释放资源
			deInitComm();
			//打开串口获得输入、输出
			OpenCommport(sendData.path, sendData.baudRate);

			// 上面是获取设置而已 下面这个才是发送指令
			if (sendData.commandStr!=null) {
				//写串口命令
				writeCommandStr(sendData.commandStr);
			}
			/*创建一个读取响应的Thread*/
			mReadThread = new ReadThread(sendData, listener);
			mReadThread.start();
		} catch (SecurityException e) {
			// DisplayError(R.string.error_security);
		} catch (IOException e) {
			// DisplayError(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			// DisplayError(R.string.error_configuration);
		}

	}
	//写串口命令
	public void  writeCommandStr(String commandStr) throws IOException{
		byte[] text = StringUtils.hexStringToBytes(commandStr);
		mOutputStream.write(text);

	}
	/**
	 * 获取到串口通信的一个示例
	 *
	 * @param path
	 * @param baudrate
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
		mSerialPort = new SerialPort(new File(path), baudrate, 0);// 打开这个串口
		mOutputStream = mSerialPort.getOutputStream();
		mInputStream = mSerialPort.getInputStream();

	}


}