package com.lxkj.pos.bean;

import java.io.Serializable;
/**
 * @author levy
 * @creation 2015-7-20 下午2:46:59
 * @instruction 窗口发送数据
 */
public class SerialPortSendData implements Serializable {
	/**
	 * 设备的地址
	 */
	public String path;
	/**
	 * 波特率
	 */
	public int baudRate;
	/**
	 * 字符串指令
	 */
	public String commandStr=null;
	/**
	 * 成功的标志
	 */
	public String okStr;
	/**
	 * 读取命令字
	 */
	public String CMD="81";
	/**
	 * 读取的身份证号长度
	 */
	public int numberLength = 36;
	/**
	 * 响应信息长度
	 */
	public int checkinfoCount = 8;
	/**
	 * 是否需要成功和失败的标志
	 */
	public boolean isFlag = true;
	/**
	 * 这个是用于判断 判断过内部的读取用的
	 */
	public boolean isReadData = false;


	/**
	 * 打包读取相关命令的类
	 * 读取身份证号
	 * @param path 地址
	 * @param baudRate 波特率
	 * @param commandStr 读取命令帧
	 * @param numberLength 读取身份证号的长度
	 * @param CMD 响应命令字
	 */
	public SerialPortSendData(String path, int baudRate, String commandStr, int numberLength,String CMD) {
		this.path = path;
		this.baudRate = baudRate;
		this.commandStr = commandStr;
		this.numberLength = numberLength;
		this.CMD=CMD;
	}

	/**
	 * 打包读取相关命令的类
	 * 读取81H响应
	 *	@param path 串口地址
	 *	@param baudRate 波特率
	 * @param checkinfoCount 读取的数据的长度
	 * @param CMD 命令字
	 */
	public SerialPortSendData(String path, int baudRate,int checkinfoCount,String CMD) {
		this.path = path;
		this.baudRate = baudRate;
		this.checkinfoCount = checkinfoCount;
		this.CMD=CMD;
		commandStr=null;
	}
}