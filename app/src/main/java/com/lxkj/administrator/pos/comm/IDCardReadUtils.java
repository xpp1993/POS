package com.lxkj.administrator.pos.comm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.lxkj.administrator.pos.bean.IdCardBean;
import com.lxkj.administrator.pos.bean.SerialPortSendData;
import com.lxkj.administrator.pos.dao.IDCardListener;
import com.lxkj.administrator.pos.dao.IReciverListener;
import com.lxkj.administrator.pos.utils.CodeUtils;
import com.lxkj.administrator.pos.utils.StringUtils;

/**
 * @author levy
 * @date 2016-11-22 11:27:36
 * @ClassName: IDCardReadUtils
 * @Description:  ���֤��ȡ�Ĺ����� 
 */
public class IDCardReadUtils {
	private final Context context;
	private IDCardListener listener;
	private SerialPortSendData sendData;
	private IDCardDevicesUtils device;
	private String devicessAddress;
	private int bauteRate;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				readCheckStata(devicessAddress, 19200);
				break;
			}
		};
	};
	/**
	 * @param context �����Ķ���
	 * @param listener �ɹ���ȡ���֤��Ϣ�Ļص�
	 */
	public IDCardReadUtils(Context context,IDCardListener listener){
		this.context = context;
		this.listener=listener;

	}

	/**
	 * ��Ϊ2���ߣ�
	 * 1.��ȡ��Ӧ���81H����
	 * 	����86 30 33 81 e4 16
	 *  д��863033117416
	 * @param devicessAddress ���ڵ�ַ
	 * @param bauteRate ������
	 */
	public void readCheckStata(String devicessAddress,int bauteRate){
		this.devicessAddress=devicessAddress;
		this.bauteRate=bauteRate;
		setNull();
		sendData =new SerialPortSendData(devicessAddress, bauteRate,8,"1");//����
		device = new IDCardDevicesUtils();
		device.toSend(context, sendData, new IReciverListener() {
			@Override
			public void onReceived(String receviceStr) {
				try {
					device.writeCommandStr("863033117416");//��Ӧ11Hָ��863033117416
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				device.deInitComm();
				//��������81H��
				getIdCardInfo();
			}
			@Override
			public void onFail(String fialStr) {
				device.deInitComm();
				mHandler.sendEmptyMessageDelayed(1, 1000);
			}
			@Override
			public void onErr(Exception e) {

			}
		});
	}
	/** 
	 * 2.��ȡ��Ӧ���֤�ı���Ϣ
	 *  д��86 30 33 20 83 16
	 *  ����86 30 33 90 +��256���֤�ı���Ϣ��+CRC+16
	 */
	public void getIdCardInfo(){
		Log.i("getIdCardInfo", "getIdCardInfo--------");

		sendData = new SerialPortSendData(devicessAddress,bauteRate,
				"863033208316",256,"90");
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
			}
			@Override
			public void onFail(String fialStr) {
				device.deInitComm();
				mHandler.sendEmptyMessageDelayed(1, 1000);
			}

			@Override
			public void onErr(Exception e) {
				//                MyToast.shortShow(getActivity(), "���֤��ȡʧ��");
			}

		});
	}

	/**
	 * ���֤��Ϣ����
	 * 86 30 33 90 +��256���֤�ı���Ϣ��+CRC+16
	 * @param dataStr
	 */
	@SuppressLint("NewApi")
	public IdCardBean getIdCardDataBean(String dataStr,IdCardBean idCard) throws UnsupportedEncodingException{
		Log.i("idcard_str",dataStr);
		idCard = new IdCardBean();
		byte[] data = StringUtils.hexStringToBytes(dataStr);
		Log.i("--------------��ȡ�ɹ���ʮ�������ַ���-------------",dataStr.length()+"");
		Log.i("--------------��ȡ�ɹ���byte����-------------",data.length+"");
		if(data.length>256){
			//1.������Ϣ����
			byte[] idWordbytes = Arrays.copyOfRange(data, 4, 260);
			idCard.setName( new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes,0, 30))).trim());
			//�Ա����⴦��
			idCard.setGender(CodeUtils.getGender(new String(StringUtils.toChar(Arrays.copyOfRange(idWordbytes, 30, 32))).trim()));
			//��������⴦��
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
	 * ��ն���
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

//
//
//	public static byte[] hex2byte(String hex) {
//		int len = (hex.length() / 2);
//		byte[] result = new byte[len];
//		char[] achar = hex.toCharArray();
//		for (int i = 0; i < len; i++) {
//			int pos = i * 2;
//			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
//		}
//		Log.i("===========result--length===========",result.length+"");
//		return result;
//	}
//
//	private static byte toByte(char c) {
//		byte b = (byte) "0123456789ABCDEF".indexOf(c);
//		return b;
//	}

	//    public static byte[] hex2byte(String str) { // �ַ���ת������
	//        if (str == null)
	//            return null;
	//        str = str.trim();
	//        int len = str.length();
	//        if (len == 0 || len % 2 == 1)
	//            return null;
	//        byte[] b = new byte[len / 2];
	//        try {
	//            for (int i = 0; i < str.length(); i += 2) {
	//                b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
	//            }
	//            return b;
	//        } catch (Exception e) {
	//            return null;
	//        }
	//    }


}