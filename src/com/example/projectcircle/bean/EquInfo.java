package com.example.projectcircle.bean;

public class EquInfo {
	// id
	private String id;
	// �û�id
	private String uid;
	// �豸����
	private String ename;
	// �����豸�����ͣ�����������ȣ�ҵ��Χ
	private String etype;
	// Ʒ��
	private String ebrand;
	// �ͺ�
	private String emodel;
	// ��ʽ ��ж��
	private String estyle;
	// ���� ��λ
	private String eweight;
	// ����
	private String enumber;
	// ����
	private String eload;
	
	private String headimage;
	// ��������
//	private String createtime;

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public String getEbrand() {
		return ebrand;
	}

	public void setEbrand(String ebrand) {
		this.ebrand = ebrand;
	}

	public String getEmodel() {
		return emodel;
	}

	public void setEmodel(String emodel) {
		this.emodel = emodel;
	}

	public String getEstyle() {
		return estyle;
	}

	public void setEstyle(String estyle) {
		this.estyle = estyle;
	}

	public String getEweight() {
		return eweight;
	}

	public void setEweight(String eweight) {
		this.eweight = eweight;
	}

	public String getEnumber() {
		return enumber;
	}

	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}

	public String getEload() {
		return eload;
	}

	public void setEload(String eload) {
		this.eload = eload;
	}

//	public String getCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}

	@Override
	public String toString() {
		return "EquInfo [id=" + id + ", uid=" + uid + ", ename=" + ename
				+ ", etype=" + etype + ", ebrand=" + ebrand + ", emodel="
				+ emodel + ", estyle=" + estyle + ", eweight=" + eweight
				+ ", enumber=" + enumber + ", eload=" + eload + ", createtime="
				+ /*createtime + */"]";
	}

}
