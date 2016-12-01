package com.lxkj.pos.utils;
/**
 * 民族选择类
 * @author levy
 *
 */
public class CodeUtils {
	public static final String[] sexCode={"未知","男","女"};
	public static final String[] nationCode={
			"未知",//0
			"汉族",//1
			"蒙古族",//2
			"回族",//3
			"藏族",
			"维吾尔族",
			"苗族",
			"彝族",
			"壮族",
			"布依族",
			"朝鲜族",
			"满族",
			"侗族",
			"瑶族",
			"白族",
			"土家族",
			"哈尼族",
			"哈萨克族",
			"傣族",
			"黎族",
			"傈僳族",
			"佤族",
			"畲族",
			"高山族",
			"拉祜族",
			"水族",
			"东乡族",
			"纳西族",
			"景颇族",
			"柯尔克孜族",
			"土族",
			"达斡尔族",
			"仫佬族",
			"羌族",
			"布朗族",
			"撒拉族",
			"毛南族",
			"仡佬族",
			"锡伯族",
			"阿昌族",
			"普米族",
			"塔吉克族",
			"怒族",
			"乌孜别克族",
			"俄罗斯族",
			"鄂温克族",
			"德昂族",
			"保安族",
			"裕固族",
			"京族",
			"塔塔尔族",
			"独龙族",
			"鄂伦春族",
			"赫哲族",
			"门巴族",
			"珞巴族",
			"基诺族"};

	/**
	 * 根据编码获取相应民族
	 * @param nation
	 * @return 相应民族
	 */
	public static String getNationNameById(String nation) {
		String str="汉";
		int code=Integer.parseInt(nation);
		return str=nationCode[code];

	}
	/**
	 *  根据性别码获得性别
	 * @param gender
	 * @return性别：'未知'or'男'or'女'
	 */
	public static String getGender(String gender) {
		String str="未知";
		int code=Integer.parseInt(gender);
		return str=sexCode[code];

	}


}

