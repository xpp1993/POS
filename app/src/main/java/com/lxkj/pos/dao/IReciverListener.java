package com.lxkj.pos.dao;


/**
 * @author levy
 * @creation 2016-11-22 17:54:56
 * @instruction 接受回调类
 */
public interface IReciverListener {
	/**
	 * 接收成功
	 * @param receviceStr
	 */
	public abstract void onReceived(String receviceStr);
	/**
	 * 出错
	 */
	public abstract void onFail(String fialStr);

	/**
	 * 出现异常
	 *
	 * @param e
	 */
	public abstract void onErr(Exception e);

}
