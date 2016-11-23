package com.lxkj.administrator.pos.dao;


/**
 * @author levy
 * @creation 2016-11-22 17:54:56
 * @instruction ���ܻص���
 */
public interface IReciverListener {

	public abstract void onReceived(String receviceStr);

	public abstract void onFail(String fialStr);

	public abstract void onErr(Exception e);

}
