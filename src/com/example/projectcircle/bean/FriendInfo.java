package com.example.projectcircle.bean;

public class FriendInfo {
	// id
	private String id;
	// ������Ӻ��ѵ��û�id��Aid
	private String aid;
	// ��Ҫ��֤����������û�id��Bid
	private String bid;
	// ״̬��status(��Ϊ����1������Ϊ����0�����Ǻ��Ѿ�û�м�¼)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FriendInfo [id=" + id + ", aid=" + aid + ", bid=" + bid
				+ ", status=" + status + "]";
	}

}
