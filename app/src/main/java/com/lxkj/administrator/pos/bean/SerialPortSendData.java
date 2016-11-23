package com.lxkj.administrator.pos.bean;

import java.io.Serializable;

public class SerialPortSendData implements Serializable {
	/**
	 * �豸�ĵ�ַ
	 */
	public String path;
	/**
	 * ������
	 */
	public int baudRate;
	/**
	 * ָ��
	 */
	public String commandStr;
	/**
	 * �ɹ��ı�־
	 */
	public String okStr;
	/**
	 * ���֤����Ӧ�����־
	 */
	public String numberStr="96";
	/**
	 * ��Ӧ��Ϣ81H��־
	 */
	public String checkinfoStr="81";
	/**
	 * ��ȡ�����֤�ų���
	 */
	public int numberLength = 36;
	/**
	 * ��Ӧ��Ϣ����
	 */
	public int checkinfoCount = 8;
	/**
	 * �Ƿ���Ҫ�ɹ���ʧ�ܵı�־
	 */
	public boolean isFlag = true;
	/**
	 * ����������ж� �жϹ��ڲ��Ķ�ȡ�õ�
	 */
	public boolean isReadData = false;


	/**
	 * ��ȡ���֤��
	 * @param path ��ַ
	 * @param baudRate ������
	 * @param commandStr ��ȡ����
	 * @param numberLength ��ȡ���֤�ŵĳ���
	 * @param numbweStr ��Ӧ���֤�ı���Ϣ�ı�־
	 */
	public SerialPortSendData(String path, int baudRate, String commandStr, int numberLength,String numbweStr) {
		this.path = path;
		this.baudRate = baudRate;
		this.commandStr = commandStr;
		this.numberLength = numberLength;
		this.numberStr=numbweStr;
	}



	/**
	 * ��ȡ81H��Ӧ
	 *	@param path ���ڵ�ַ
	 *	@param baudRate ������
	 * @param checkinfoCount ��ȡ�����ݵĳ���  
	 * @param checkinfoStr 81H��Ӧ��־
	 */
	public SerialPortSendData(String path, int baudRate,int checkinfoCount,String checkinfoStr) {
		this.path = path;
		this.baudRate = baudRate;
		this.checkinfoCount = checkinfoCount;
		this.checkinfoStr=checkinfoStr;
		this.commandStr=null;

	}
}