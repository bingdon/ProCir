package com.example.projectcircle.bean;

import java.io.Serializable;


public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	// �绰
	private String tel;
	// ����
	private String age;
	// �û���
	private String username;
	// ����
	private String password;
	// �û�����
	private String type;
	// ͷ��
	private String headimage;
	// ���ڵ�
	private String address;
	// �豸
	private String equipment;
	// ��������
	private String birthday;
	// ҵ��
	private String business;
	// �Ƿ���Ȧ����ʾ�绰��1Ĭ��Ϊ��ʾ��
	private String accept;
	// ����
	private double lon;
	// γ��
	private double lat;
	// ȫ��ַ
	private String rplace;
	// ?
	private String displayType;
	// ����ǩ��
	private String sign;
	// ���˼��
	private String info;
	// ע��ʱ��
	private String createtime;
	// ��˾����
	String companyname;
	// ����û�ĵص�
	String place;
	// ��Ȥ����
	String hobby;
	// ɾ������ʱ�õ���id
	private String ccid;
	//�ϴε�¼��ʱ��
	private String lastlogintime;	
	// �������Ⱥ���id
	private String gid;
	public String getRplace() {
		return rplace;
	}

	public void setRplace(String rplace) {
		this.rplace = rplace;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double d) {
		this.lon = d;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double d) {
		this.lat = d;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", tel=" + tel + ", age=" + age
				+ ", username=" + username + ", password=" + password
				+ ", type=" + type + ", headimage=" + headimage + ", address="
				+ address + ", equipment=" + equipment + ", birthday="
				+ birthday + ", business=" + business + ", accept=" + accept
				+ ", lon=" + lon + ", lat=" + lat + ", rplace=" + rplace
				+ ", displayType=" + displayType + ", sign=" + sign + ", info="
				+ info + ", createtime=" + createtime + ", companyname="
				+ companyname + ", place=" + place + ", hobby=" + hobby + "]";
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}




}
