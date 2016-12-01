package com.lxkj.pos.dao;

import com.lxkj.pos.bean.IdCardBean;

/**
 * 读取成功以后，身份证信息的回调接口
 */
public interface IDCardListener{

	/**
	 * 读到身份证信息后的回调
	 * @param bean 有身份证信息的实体类
	 */
	public abstract void onInfo(IdCardBean bean);
	/**
	 * 读取失败的回调
	 * @param str 失败信息
	 */
	public abstract void unInfo(String str);

}