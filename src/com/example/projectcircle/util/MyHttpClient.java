package com.example.projectcircle.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @Description: HttpClient实现籄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
 * 
 */
public class MyHttpClient {

	public static final String BASE_URL = "http://115.28.81.148/project/api/";
	/**
	 * 登录和注冄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public static final String login_url = "http://115.28.81.148/project/api/findUser?tel=";
	public static final String sigin_url = "http://115.28.81.148/project/api/postUser?tel=";
	/**
	 * 完善信息
	 */
	public static final String completeall_url = "http://115.28.81.148/project/api/postUser?id=";
	public static final String complete_url = "http://115.28.81.148/project/api/updateEqu?uid=";
	public static final String completeD_url = "http://115.28.81.148/project/api/updateDriver?uid=";
	public static final String completeUser_url = "http://115.28.81.148/project/api/updateUser?id=";
	public static final String updateMyInfo_url = "http://115.28.81.148/project/api/update?id=";
	public static final String uploadequheadimg_url = "http://115.28.81.148/project/api/uploadEquHeadimage";
	/**
	 * 用户详情 附近亄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�近群组 跨区域群组1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�新位置
	 */
	// 用户详情
	public final static String userdetail_url = "http://115.28.81.148/project/api/UserDetail?id=";
	// 设备的详惄1�7
	public final static String equdetail_url = "http://115.28.81.148/project/api/findByUid?uid=";
	// 按照type的不同类型解析的userDetail
	public final static String userotherdetail_url = "http://115.28.81.148/project/api/userOtherDetail?id=";
	// 附近亄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String nearuser_url = "http://115.28.81.148/project/api/nearUser?id=";
	// 筛�1ￄ1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String select_url = "http://115.28.81.148/project/api/userFilter?address=";
	// 附近群组
	public final static String neargroup_url = "http://115.28.81.148/project/api/nearGroup?radius=";
	// 跨区域群组1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String grouplist_url = "http://115.28.81.148/project/api/groupList";
	// 群详惄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�id
	public final static String groupdetail_url = "http://115.28.81.148/project/api/GroupDetail?id=";
	// 更新位置
	public final static String updateP_url = "http://115.28.81.148/project/api/updatePosition?id=";
	// 好友列表显示
	public static final String friend_url = "http://115.28.81.148/project/api/findFriends?id=";
	public static final String findconRegiest_url = "http://115.28.81.148/project/api/isRegiest?";
	/**
	 * 创建群组
	 */
	public final static String postgroup_url = "http://115.28.81.148/project/api/postGroup?uid=";
	// 某区域的群组敄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String countgroup_url = "http://115.28.81.148/project/api/countGroup?key=";
	// 查找群成呄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String findmember_url = "http://115.28.81.148/project/api/findMember?gid=";
	// 删除群成呄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String delmember_url = "http://115.28.81.148/project/api/deleteMember?uid=";
	// 查找自己创建的群组1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String findmyselfgroup_url = "http://115.28.81.148/project/api/findCreateGroup?uid=";
	public final static String findmygroup_url = "http://115.28.81.148/project/api/findGroup?uid=";
	/**
	 * 好友
	 */
	// 申请好友接口
	public final static String applyfriend_url = "http://115.28.81.148/project/api/askFriends?aid=";
	public final static String searchfriend_url = "http://115.28.81.148/project/api/searchFriends?key=";
	public final static String verifyfriend_url = "http://115.28.81.148/project/api/askFriends?aid=";
	public final static String friendrequest_url = "http://115.28.81.148/project/api/verifyFriends?bid=";
	public final static String befriend_url = "http://115.28.81.148/project/api/beFriends?id=";
	public final static String denyfriend_url = "http://115.28.81.148/project/api/delFriends?id=";
	// 根据电话号码查找用户
	public final static String finduserbytel_url = "http://115.28.81.148/project/api/findUserByTel?tel=";
	/**
	 * 工作接口
	 */
	// 发表心情,不含图片
	public final static String post_mood = "http://115.28.81.148/project/api/postMood?uid=";
	// 发表心情中的图片
	public final static String postmood_land = "http://115.28.81.148/project/api/postMoodLand?userid=";
	// 发表心情中的图片
	public final static String postmood_img = "http://115.28.81.148/project/api/postMoodImg";
	// 发表评论
	public final static String postcomment_url = "http://115.28.81.148/project/api/postMoodComment?moodid=";
	// 发布工程作业
	public final static String postjob_url = "http://115.28.81.148/project/api/postJob?uid=";
	// 工程作业列表和司机需求列衄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String joblist_url = "http://115.28.81.148/project/api/findJobByType?type=";
	// 扄1�7 扄1�7仄1�7
	// public final static String joblist_url =
	// "http://115.28.81.148/project/api/findJobByType?type=";
	// 状�1ￄ1�7�1�7�1�7�1�7�列衄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String my_mood = "http://115.28.81.148/project/api/userMood?id=";
	public final static String listmood_url = "http://115.28.81.148/project/api/listMood?userid=";
	// 工程作业详情和司机需求详惄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String jobdetail_url = "http://115.28.81.148/project/api/JobDetail?id=";
	// 扄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�工作接叄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String alljob_url = "http://115.28.81.148/project/api/findAllJob";
	// 扄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�商家接叄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public final static String findshangjia_url = "http://115.28.81.148/project/api/findByType?type=";
	// 根据类型查找商家设备
	public final static String findetype_url = "http://115.28.81.148/project/api/findBusiness?business=";
	// 移动商家筛�1ￄ1�7�1�7�1�7
	public final static String moveshopselect_url = "http://115.28.81.148/project/api/userFilterByBusEqu?equ=";
	/**
	 * 图片地址
	 */
	public static final String IMAGE_URL = "http://115.28.81.148/project/upload/";
	// 上传个人头像
	public final static String headurl = "http://115.28.81.148/project/api/uploadHeadimage";
	// 上传群组头像
	public final static String gheadurl = "http://115.28.81.148/project/api/uploadGroupHeadimage";
	/**
	 * 发�1ￄ1�7�1�7�1�7�1�7�聊天消恄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public final static String postmessages_url = "http://115.28.81.148/project/api/postMessages?userid=";
	// 判断用户输入密码是否正确
	public final static String pwdistrue_url = "http://115.28.81.148/project/api/psIstrue?id=";

	public final static String PWD_CHANGE_URL = "http://115.28.81.148/project/api/psIstrue";
	// 意见反馈接口
	public final static String postopinion_url = "http://115.28.81.148/project/api/postOpinion?uid=";

	private static final String DEL_EQU_URL = BASE_URL + "deleteEqu";
	/**
	 * 登录和注冄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	// 登录

	private static AsyncHttpClient client = new AsyncHttpClient();

	public void doLogin(String tel, String password,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(login_url + tel + "&password=" + password, params, res);
			Log.i("login_url", login_url + tel + "&password=" + password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 注册
	public void doSigin(String tel, String password, String username,
			String birthday, String address, String type, String equipment,
			String business, String accept, double lon, double lat,
			String rplace, String age, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(sigin_url + tel + "&password=" + password
					+ "&username=" + username + "&birthday=" + birthday
					+ "&address=" + address + "&type=" + type + "&equipment="
					+ equipment + "&business=" + business + "&accept=" + accept
					+ "&lon=" + lon + "&lat=" + lat + "&rplace=" + rplace
					+ "&age=" + age, params, res);
			Log.i("sigin_url", sigin_url + tel + "&password=" + password
					+ "&username=" + username + "&birthday=" + birthday
					+ "&address=" + address + "&type=" + type + "&equipment="
					+ equipment + "&business=" + business + "&accept=" + accept
					+ "&lon=" + lon + "&lat=" + lat + "&rplace=" + rplace
					+ "&age=" + age);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 完善信息 机主
	 */
	public void uploadEqu(String uid, String type, String ename, String ebrand,
			String etype, String emodel, String enumber,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&ebrand=" + ebrand + "&etype=" + etype
					+ "&emodel=" + emodel + "&enumber=" + enumber, params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&ebrand=" + ebrand + "&etype="
					+ etype + "&emodel=" + emodel + "&enumber=" + enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 装载轄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�它
	public void CompMasterLoad(String uid, String type, String ename,
			String ebrand, String eweight, String emodel, String enumber,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&ebrand=" + ebrand + "&eweight=" + eweight
					+ "&emodel=" + emodel + "&enumber=" + enumber, params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&ebrand=" + ebrand + "&eweight="
					+ eweight + "&emodel=" + emodel + "&enumber=" + enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 平板轄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	public void CompMasterFlat(String uid, String type, String ename,
			String eload, String enumber, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(complete_url + uid + "&type=" + type + "&ename="
					+ ename + "&eload=" + eload + "&enumber=" + enumber,
					params, res);
			Log.i("complete_url", complete_url + uid + "&type=" + type
					+ "&ename=" + ename + "&eload=" + eload + "&enumber="
					+ enumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 完善信息 公司
	 */
	public void CompleteCompany(String uid, String companyname,
			String businessinfo, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeUser_url + uid + "&companyname=" + companyname
					+ "&businessinfo=" + businessinfo, params, res);
			Log.i("completeUser_url", completeUser_url + uid + "&companyname="
					+ companyname + "&businessinfo=" + businessinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 完善信息 司机
	 */
	public void CompleteDriver(String id, String type, String driveryears,
			String nequ, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeD_url + id + "&type=" + type + "&driveryears="
					+ driveryears + "&nequ=" + nequ, params, res);
			Log.i("completeD_url", completeD_url + id + "&type=" + type
					+ "&driveryears=" + driveryears + "&nequ=" + nequ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新个人信息
	 */
	public void updateMyInfo(String id, String username, String age,
			String sign, String info, String type, String equipment,
			String accept, String address, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(updateMyInfo_url + id + "&username=" + username
					+ "&age =" + age + "&sign=" + sign + "&info=" + info
					+ "&type=" + type + "&equipment=" + equipment + "&accept="
					+ accept + "&address=" + address, params, res);
			Log.i("updateMyInfo_url", updateMyInfo_url + id + "&username="
					+ username + "&age =" + age + "&sign=" + sign + "&info="
					+ info + "&type=" + type + "&equipment=" + equipment
					+ "&accept=" + accept + "&address=" + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上传头像
	public void postHeadImage(String id, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("id", id);
			params.put("headimage", headimage);
			HttpUtil.post(headurl, params, res);
			// HttpUtil.post(headurl + id + "&headimage=" + headimage, params,
			// res);
			// System.out.println(headurl + id + "&headimage=" + headimage);
			Log.i("headurl", headurl + "id=" + id + ",headimage=" + headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加设备的三张照牄1�7
	 */
	public void upLoadEquHeadImage(String uid, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("uid", uid);
			params.put("headimage", headimage);
			HttpUtil.post(uploadequheadimg_url, params, res);
			Log.i("uploadequheadimg_url", uploadequheadimg_url + "uid=" + uid
					+ ",headimage=" + headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 完善信息 个人信息
	public void BaseInfo(String id, String sign, String place, String hobby,
			String info, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(completeUser_url + id + "&sign=" + sign + "&place="
					+ place + "&hobby=" + hobby + "&info=" + info, params, res);
			Log.i("completeUser_url", completeUser_url + id + "&sign=" + sign
					+ "&place=" + place + "&hobby=" + hobby + "&info=" + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户详情
	 */
	public void UserDetail(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(userdetail_url + id, params, res);
			Log.i("userdetail_url", userdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设备详情
	 */
	public void EquDetail(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(equdetail_url + uid, params, res);
			Log.i("equdetail_url", equdetail_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按type类型不同解析的userDetail
	 */
	public void UserDetail2(String id, String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(userotherdetail_url + id + "&type=" + type, params,
					res);
			Log.i("userdetail_url", userdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 附近亄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void UserFilter(String address, String type, String equ, double lat,
			double lon, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(select_url + address + "&type=" + type + "&equ=" + equ
					+ "&lat=" + lat + "&lon=" + lon, params, res);
			Log.i("select_url", select_url + address + "&type=" + type
					+ "&equ=" + equ + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绛涢�鍔熻兘
	 */
	public void NearUser(String id, int radius, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(nearuser_url + id + "&radius=" + radius + "&lat="
					+ lat + "&lon=" + lon, params, res);
			Log.i("nearuser_url", nearuser_url + id + "&radius=" + radius
					+ "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上传群组头像
	public void postGroupHeadImage(String id, String headimage,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("uid", id);
			params.put("headimage", headimage);
			HttpUtil.post(gheadurl, params, res);
			Log.i("gheadurl", gheadurl + "uid==" + id + ",headimage=="
					+ headimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 附近群组
	 */
	public void NearGroup(int radius, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(
					neargroup_url + radius + "&lat=" + lat + "&lon=" + lon,
					params, res);
			Log.i("neargroup_url", neargroup_url + radius + "&lat=" + lat
					+ "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跨区域群组1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void GroupList(AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(grouplist_url, params, res);
			Log.i("grouplist_url", grouplist_url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新坐标
	 */
	public void updatePosition(String id, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(updateP_url + id + "&lat=" + lat + "&lon=" + lon,
					params, res);
			Log.i("updateP_url", updateP_url + id + "&lat=" + lat + "&lon="
					+ lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建群组
	 */
	public void postGroup(String uid, String gname, String gaddress,
			String content, String glimit, double lat, double lon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postgroup_url + uid + "&gname=" + gname + "&gaddress="
					+ gaddress + "&content=" + content + "&glimit=" + glimit
					+ "&lat=" + lat + "&lon=" + lon, params, res);
			Log.i("postgroup_url", postgroup_url + uid + "&gname=" + gname
					+ "&gaddress=" + gaddress + "&content=" + content
					+ "&glimit=" + glimit + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 群详惄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void GroupDetails(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(groupdetail_url + id, params, res);
			Log.i("groupdetail_url", groupdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 某区域的群组敄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void CountGroup(String key, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(countgroup_url + key, params, res);
			Log.i("countgroup_url", countgroup_url + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找群成呄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void findMember(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmember_url + id, params, res);
			Log.i("findmember_url", findmember_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除群成呄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void deleteMember(String uid, String gid,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(delmember_url + uid + "&gid=" + gid, params, res);
			Log.i("delmember_url", delmember_url + uid + "&gid=" + gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断通讯录的电话号码是否已注册为工程圈用戄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void contactIsRegiest(String tel, String id,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.add("tel", tel);
			HttpUtil.post(findconRegiest_url + "&id=" + id, params, res);
			Log.i("findmyselfregist_url", findconRegiest_url + tel + "&id="
					+ id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏌ユ壘鑷繁寤虹殑缇ょ粍
	 */
	public void findCreatGroup(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmyselfgroup_url + uid, params, res);
			Log.i("findmyselfgroup_url", findmyselfgroup_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我的群组列表
	 */
	public void findGroup(String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findmygroup_url + uid, params, res);
			Log.i("findmygroup_url", findmygroup_url + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发布工作
	 */
	public void PostJob(String uid, String title, String location, String type,
			String detail, String name, String tel, Double jlat, Double jlon,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postjob_url + uid + "&title=" + title + "&location="
					+ location + "&type=" + type + "&detail=" + detail
					+ "&name=" + name + "&tel=" + tel + "&lat=" + jlat
					+ "&lon=" + jlon, params, res);
			Log.i("postjob_url", postjob_url + uid + "&title=" + title
					+ "&location=" + location + "&type=" + type + "&detail="
					+ detail + "&name=" + name + "&tel=" + tel + "&lat=" + jlat
					+ "&lon=" + jlon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发布心情内容，不含图牄1�7
	 */
	public void PostMood(String uid, String context,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.post(post_mood + uid + "&context=" + context// +"&headimage="+headimge
			, params, res);
			Log.i("post_mood", post_mood + uid + "&context=" + context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发表评论
	 */
	public void PostComment(String moodid, String content, String userid,
			String commentid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.post(postcomment_url + moodid + "&content=" + content
					+ "&userid=" + userid// +"&headimage="+headimge
					+ "&commentid=" + commentid, params, res);
			Log.i("postcomment_url", postcomment_url + moodid + "&content="
					+ content + "&userid=" + userid// +"&headimage="+headimge
					+ "&commentid=" + commentid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发布心情内容中的图片
	 */
	public static void postMoodImg(String moodid, String moodimage2,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			params.put("moodid", moodid);
			params.put("moodpicStr", moodimage2);
			HttpUtil.post(postmood_img, params, res);
			Log.i("postmood_img", postmood_img + moodid + "&moodpicStr="
					+ moodimage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点赞
	 */
	public void postMoodLand(String userid, String moodid,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postmood_land + userid + "&moodid=" + moodid, params,
					res);
			Log.i("postmood_land", postmood_land + userid + "&moodid=" + moodid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 好友的状态列衄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void listMood(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(listmood_url + id, params, res);
			Log.i("listmood_url", listmood_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自己的状态列衄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void userMood(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(my_mood + id, params, res);
			Log.i("my_mood", my_mood + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 工作列表
	 */
	public void JobList(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 司机霄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�列衄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void JobList1(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(joblist_url + type, params, res);
			Log.i("joblist_url", joblist_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 工作详情
	 */
	public void JobDetail(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(jobdetail_url + id, params, res);
			Log.i("jobdetail_url", jobdetail_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找扄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�工佄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void AllJobList(AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(alljob_url, params, res);
			Log.i("alljob_url", alljob_url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找扄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�商宄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 */
	public void AllBusiness(String type, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findshangjia_url + type, params, res);
			Log.i("findshangjia_url", findshangjia_url + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据类型查找商家设备
	 */
	public void findEtype(String etype, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(findetype_url + etype, params, res);
			Log.i("findetype_url", findetype_url + etype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动商家筛�1ￄ1�7�功胄1�7�1�7
	 */
	public void userFilterByBusEqu(String equ, String bus, Double lat,
			Double lon, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(moveshopselect_url + equ + "&bus=" + bus + "&lat="
					+ lat + "&lon=" + lon, params, res);
			Log.i("moveshopselect_url", moveshopselect_url + equ + "&bus="
					+ bus + "&lat=" + lat + "&lon=" + lon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 申请好友
	 */
	public void applyfriend(String aid, String bid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(applyfriend_url + aid + "&bid=" + bid, params, res);
			Log.i("applyfriend_url", applyfriend_url + aid + "&bid=" + bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找好友
	 */
	public void searchfriend(String key, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(searchfriend_url + key, params, res);
			Log.i("searchfriend_url", searchfriend_url + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 好友验证
	 */
	public void VerifyFriend(String aid, String bid, String info,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(verifyfriend_url + aid + "&bid=" + bid + "&info="
					+ info, params, res);
			Log.i("verifyfriend_url", verifyfriend_url + aid + "&bid=" + bid
					+ "&info=" + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 请求信息
	 */
	public void FriendRequestMessage(String bid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(friendrequest_url + bid, params, res);
			Log.i("friendrequest_url", friendrequest_url + bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证同意成为好友
	 */
	public void beFriends(Object accept_id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(befriend_url + accept_id, params, res);
			Log.i("befriend_url", befriend_url + accept_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拒绝成为好友
	 */
	public void denyFriends(Object deny_id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(denyfriend_url + deny_id, params, res);
			Log.i("denyfriend_url", denyfriend_url + deny_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据手机号查找好友信恄1�7
	 */
	public void findUserByTel(String tel, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(finduserbytel_url + tel, params, res);
			Log.i("finduserbytel_url", finduserbytel_url + tel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 好友列表
	 */
	public void findfriend(String id, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(friend_url + id, params, res);
			Log.i("friend_url", friend_url + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发�1ￄ1�7�1�7�1�7�1�7�聊天消恄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 * 
	 */
	public void postMessages(String userid, String sendid, String content,
			AsyncHttpResponseHandler res) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postmessages_url + userid + "&sendid=" + sendid
					+ "&content=" + content, params, res);
			Log.i("postmessages_url", postmessages_url + userid + "&sendid="
					+ sendid + "&content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postMessages2Group(String userid, String gid, String content,
			AsyncHttpResponseHandler res) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		try {

			HttpUtil.get(postmessages_url + null + "&sendid=" + userid
					+ "&gid=" + gid + "&content=" + content, params, res);
			Log.i("postmessages_url", postmessages_url + null + "&sendid="
					+ userid + "&gid=" + gid + "&content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加入群组
	 * 
	 * @param gid
	 *            群组ID.
	 * @param uid
	 *            用户ID
	 * @param handler
	 *            处理返回
	 */
	public static void askAddGroup(String gid, String uid, String info,
			AsyncHttpResponseHandler handler) {

		RequestParams params = new RequestParams();
		params.put("gid", gid);
		params.put("uid", uid);
		params.put("info", info);
		Log.i("����", BASE_URL + "askMember");
		HttpUtil.post(BASE_URL + "askMember", params, handler);
	}

	/**
	 * 查询群成呄1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
	 * 
	 * @param gid
	 *            群组ID
	 * @param handler
	 *            处理返回
	 */
	public static void verifyMember2Group(String gid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("gid", gid);
		HttpUtil.post(BASE_URL + "verifyMember", params, handler);
	}

	/**
	 * 同意加入群组
	 * 
	 * @param id
	 *            请求ID
	 * @param handler
	 */
	public static void beMember2GroupMem(String id,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		HttpUtil.post(BASE_URL + "beMember", params, handler);
	}

	/**
	 * 删除群组成员
	 * 
	 * @param gid
	 *            群组ID
	 * @param uid
	 *            用户ID
	 * @param handler
	 */
	public static void deleteMember2Group(String gid, String uid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("gid", gid);
		params.put("uid", uid);
		HttpUtil.post(BASE_URL + "deleteMember", params, handler);
	}

	/**
	 * 查询用户设备
	 * 
	 * @param uid
	 *            用户ID
	 * @param handler
	 */
	public static void findUserEqu4User(String uid,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("uid", uid);
		HttpUtil.post(BASE_URL + "findUserEqu", params, handler);
	}

	// 判断是否是群成员
	public static final String isgroupmember_url = "http://115.28.81.148/project/api/isMember?gid=";

	/**
	 * 判断是否是群成员
	 */
	public void isMember(String gid, String uid, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(isgroupmember_url + gid + "&uid=" + uid, params, res);
			Log.i("login_url", isgroupmember_url + gid + "&uid=" + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改密码中验证用户输入的密码是否正确
	public void doPwdIsTrue(String id, String pwd,String pwd_new, AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("pwd", pwd);
		params.put("npwd", pwd_new);
		try {
			new AsyncHttpClient().post(PWD_CHANGE_URL, params, res);
			// HttpUtil.get(pwdistrue_url + id + "&psw=" + psw, params, res);
			Log.i("pwdistrue_url", pwdistrue_url + id + "&psw=" + pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 意见反馈
	public void doPostOpinion(String uid, String content,
			AsyncHttpResponseHandler res) {
		RequestParams params = new RequestParams();
		try {
			HttpUtil.get(postopinion_url + uid + "&content=" + content, params,
					res);
			Log.i("pwdistrue_url", postopinion_url + uid + "&content="
					+ content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���豸
	 * @param id
	 * @param handler
	 */
	public static void deleteEqu(String id, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(DEL_EQU_URL, params, handler);
	}

}