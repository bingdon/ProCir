package com.example.projectcircle.bean;

public class JobInfo {
	private String id;// ����id
	private String type;// ��������
	private String location;// ������ַ
	private String title;// ��������
	private String detail;// ��������
	private String name;// ������ϵ��
	private String tel;// �����绰
	private String date;// ����ʱ��
	private String year;// ����ʱ�� ��
	private String month;// ����ʱ�� ��
	private String day;// ����ʱ�� ��
    private double lat;//γ��
    private double lon;//����
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "JobInfo [id=" + id + ", type=" + type + ", location="
				+ location + ", title=" + title + ", detail=" + detail
				+ ", name=" + name + ", tel=" + tel + ", date=" + date
				+ ", year=" + year + ", month=" + month + ", day=" + day + "]";
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

	public void setLat(double d) {
		this.lat = d;
	}



}
