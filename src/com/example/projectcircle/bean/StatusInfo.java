package com.example.projectcircle.bean;

public class StatusInfo {
	// id
	private String id;
	// �绰
	private String tel;
	// ����
	private String age;
	// �û���
	private String username;
	// ����
	
	// ͷ��
	private String uheadimage;
	// ����Ȧ��Ƭ
	private String mheadimage;
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

	public String getRplace() {
		return rplace;
	}

	public void setRplace(String rplace) {
		this.rplace = rplace;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
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



	@Override
	public String toString() {
		return "StatusInfo [id=" + id + ", tel=" + tel + ", age=" + age
				+ ", username=" + username + ", uheadimage=" + uheadimage
				+ ", mheadimage=" + mheadimage + ", lon=" + lon + ", lat="
				+ lat + ", rplace=" + rplace + ", displayType=" + displayType
				+ ", sign=" + sign + ", info=" + info + ", createtime="
				+ createtime + ", companyname=" + companyname + ", place="
				+ place + ", hobby=" + hobby + "]";
	}

	public String getUheadimage() {
		return uheadimage;
	}

	public void setUheadimage(String uheadimage) {
		this.uheadimage = uheadimage;
	}

	public String getMheadimage() {
		return mheadimage;
	}

	public void setMheadimage(String mheadimage) {
		this.mheadimage = mheadimage;
	}

}
