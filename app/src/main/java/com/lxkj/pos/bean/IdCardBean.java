package com.lxkj.pos.bean;

/**
 * 身份证文本信息实体
 * @author levy
 *
 */
public class IdCardBean {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 出生年月日
	 */
	private String birthday;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 签发机关
	 */
	private String issuingAuthority;
	/**
	 * 有效期起始日期
	 */
	private String startTime;
	/**
	 * 有效期截止日期
	 */
	private String startopTime;


	public IdCardBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IdCardBean(String name, String gender, String nation,
					  String birthday, String address, String idCard,
					  String issuingAuthority, String startTime, String startopTime) {
		super();
		this.name = name;
		this.gender = gender;
		this.nation = nation;
		this.birthday = birthday;
		this.address = address;
		this.idCard = idCard;
		this.issuingAuthority = issuingAuthority;
		this.startTime = startTime;
		this.startopTime = startopTime;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getIssuingAuthority() {
		return issuingAuthority;
	}
	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartopTime() {
		return startopTime;
	}
	public void setStartopTime(String startopTime) {
		this.startopTime = startopTime;
	}


}
