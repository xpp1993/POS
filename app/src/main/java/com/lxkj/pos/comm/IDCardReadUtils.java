package com.lxkj.pos.comm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import com.lxkj.pos.bean.IdCardBean;
import com.lxkj.pos.bean.SerialPortSendData;
import com.lxkj.pos.dao.IDCardListener;
import com.lxkj.pos.dao.IReciverListener;
import com.lxkj.pos.utils.CodeUtils;
import com.lxkj.pos.utils.StringUtils;

/**
 * @author levy
 * @date 2016-11-22 11:27:36
 * @ClassName: IDCardReadUtils
 * @Description:  身份证读取的工具类
 */
public class IDCardReadUtils {
	public static final String TAG = "IDCardReadUtils";
	private final Context context;
	private IDCardListener listener;
	private SerialPortSendData sendData;
	private IDCardDevicesUtils device;
	private String devicessAddress;
	private int bauteRate;
	int cout;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					cout++;
					Log.i(TAG, "正在读取第"+cout+"次");
					if (cout==500) {
						cout=0;
					}
					readCheckState();
					break;
				case 2:
					deleteData();
					break;
			}
		};
	};

	/**
	 * @param context 上下文对象
	 * @param listener 成功读取身份证信息的回调
	 */
	public IDCardReadUtils(Context context,String devicessAddress,int bauteRate,IDCardListener listener){
		this.context = context;
		this.listener=listener;
		this.devicessAddress=devicessAddress;
		this.bauteRate=bauteRate;

	}
	/**
	 * 1.检测是否有身份信息
	 * 	读：86 30 33 81 e4 16
	 *  写：863033117416
	 */
	public void readCheckState(){
		Log.i(TAG, "readCheckState-检测81H命令");
		setNull();
		//命令帧
		final String commandStr="860003111416";
		sendData =new SerialPortSendData(devicessAddress, bauteRate,8,"81");//待改
		device = new IDCardDevicesUtils();
		device.toSend(context, sendData, new IReciverListener() {
			@Override
			public void onReceived(String receviceStr) {
				try {
					device.writeCommandStr(commandStr);//响应11H指令863033117416
					device.deInitComm();
					//读到命令81H后
					getIdCardInfo();
					Thread.sleep(100);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFail(String fialStr) {
				device.deInitComm();
				mHandler.sendEmptyMessageDelayed(1, 100);
			}
			@Override
			public void onErr(Exception e) {
				listener.unInfo(""+e);
			}
		});
	}
	/**
	 * 2.读取响应身份证文本信息
	 *  写：86 00 03 20 23 16
	 *  读：86 01 03 90 +（256身份证文本信息）+CRC+16
	 */
	public void getIdCardInfo(){
		Log.i(TAG, "getIdCardInfo-获取文本信息");
		setNull();
		//命令帧
		String commandStr="860003202316";
		sendData = new SerialPortSendData(devicessAddress,bauteRate,
				commandStr,256,"90");
		device = new IDCardDevicesUtils();
		device.toSend(context, sendData, new IReciverListener() {
			@Override
			public void onReceived(String receviceStr) {
				IdCardBean bean = null ;
				try {
					bean = getIdCardDataBean(receviceStr,bean);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listener.onInfo(bean);
				device.deInitComm();
				try {
					deleteData();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFail(String fialStr) {
				device.deInitComm();
				deleteData();
				mHandler.sendEmptyMessageDelayed(1, 100);
			}

			@Override
			public void onErr(Exception e) {
				listener.unInfo(""+e);
			}

		});
	}
	/**
	 * 清楚本次记录的数据
	 */
	public  void deleteData(){
		Log.i(TAG, "deleteData-清楚本次数据");
		setNull();
		//命令帧
		String commandStr="860003121516";
		sendData = new SerialPortSendData(devicessAddress,bauteRate,
				commandStr,8,"82");
		device = new IDCardDevicesUtils();
		device.toSend(context, sendData, new IReciverListener() {
			@Override
			public void onReceived(String receviceStr) {
				Log.i(TAG, "------清楚本次数据成功------");
				device.deInitComm();
			}
			@Override
			public void onFail(String fialStr) {

			}
			@Override
			public void onErr(Exception e) {
				listener.unInfo(""+e);
			}
		});

	}
	/**
	 * 身份证信息解析处理
	 * 86 30 33 90 +（256身份证文本信息）+CRC+16
	 * @param dataStr
	 */
	@SuppressLint("NewApi")
	public IdCardBean getIdCardDataBean(String dataStr,IdCardBean idCard) throws UnsupportedEncodingException{
		idCard = new IdCardBean();
		byte[] data = StringUtils.hexStringToBytes(dataStr);
		Log.i(TAG,"读取的十六进制字符串长度:"+dataStr.length()+"");
		Log.i(TAG,"读取的byte数组长度:"+data.length+"");
		if(data.length>256){
			//1.文字信息处理
			byte[] idWordbytes = Arrays.copyOfRange(data, 4, 260);
			idCard.setName( new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,0, 30))).trim());
			//性别特殊处理
			idCard.setGender(CodeUtils.getGender(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,30, 32))).trim()));
			//名族的特殊处理
			idCard.setNation( CodeUtils.getNationNameById(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,32, 36))).trim()));
			idCard.setBirthday( new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,36, 52))).trim());
			idCard.setAddress(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,52, 122))).trim());
			idCard.setIdCard(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,122, 158))).trim());
			idCard.setIssuingAuthority(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,158, 188))).trim());
			idCard.setStartTime(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,188, 204))).trim());
			idCard.setStartopTime(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,204, 220))).trim());
		}
		return idCard;
	}

	/**
	 * 清空对象
	 */
	private void setNull() {
		// TODO Auto-generated method stub
		if (sendData!=null) {
			sendData=null;
		}
		if (device!=null) {
			device=null;
		}
	}
	private void showDialog(String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(str)
				.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {


					}
				}).setCancelable(false).create().show();
	}
}