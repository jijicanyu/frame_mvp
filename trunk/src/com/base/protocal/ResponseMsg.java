package com.base.protocal;


import java.io.Serializable;

public abstract class ResponseMsg implements Serializable
{
	protected int msgno; // 消息
	protected String msgname; // 消息名称

    public String actname="";//Activity名称
	
	protected String resuldcode = "-2"; // -1,0,1,-1重新登陆
	protected String description = "";
	
	private long lastCacheTime = 0;

	public String getResuldcode()
	{
		return resuldcode;
	}

	public void setResuldcode(String resuldcode)
	{
		this.resuldcode = resuldcode;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public abstract void init(String result);

	private boolean isOK = true;

	public boolean isOK()
	{
		return isOK;
	}

	public void setOK(boolean isOK)
	{
		this.isOK = isOK;
	}

	private String errInfo;

	public String getErrInfo()
	{
		return errInfo;
	}

	public void setErrInfo(String errInfo)
	{
		this.errInfo = errInfo;
	}

	private boolean isNetWorkError = false;

	public boolean isNetWorkError()
	{
		return isNetWorkError;
	}

	public void setNetWorkError(boolean isNetWorkError)
	{
		this.isNetWorkError = isNetWorkError;
	}
	public boolean isReLogin()
	{
		return "-1".equals(resuldcode);
	}
	public int getMsgno()
	{
		return msgno;
	}

	public void setMsgno(int msgno)
	{
		this.msgno = msgno;
	}

	public String getMsgname()
	{
		return msgname;
	}

	public void setMsgname(String msgname)
	{
		this.msgname = msgname;
	}
	

	public long getLastCacheTime() {
		return lastCacheTime;
	}

	public void setLastCacheTime(long lastCacheTime) {
		this.lastCacheTime = lastCacheTime;
	}

    public static final int UserSigning_MSGNO = 0X21001;
	public static final int Login_MSGNO = 0X21021;
    public static final int GetServiceList_MSGNO = 0X21002;
    public static final int GetAttentionServiceList_MSGNO = 0X21003;
    public static final int FileUpload_MSGNO = 0X21006;
    public static final int AvatarUpload_MSGNO = 0X210017;
    public static final int SetOrderStaute_MSGNO = 0X210025;
    public static final int VIDEOUpload_MSGNO = 0X22004;
    public static final int GetUserInfo_MSGNO = 0X21009;
    public static final int EditUserInfo_MSGNO = 0X21005;
    public static final int EditPWD_MSGNO = 0X21006;
    public static final int ResetPWD_MSGNO = 0X21007;
    public static final int Receivecode_MSGNO = 0X21008;
    public static final int GetVideoList_MSGNO = 0X21012;
    public static final int GetVideosInfo_MSGNO = 0X21052;
    public static final int GetServiceModel_MSGNO = 0X21021;
    public static final int AttentionService_MSGNO=0X231011;
    public static final int GetAppConfig_MSGNO =0X230151;

    public static final int GetMsgTypeList_MSGNO=0X231012;
    public static final int GetMessageList_MSGNO=0X231014;
    public static final int GetMessageSysList_MSGNO=0X231015;
    public static final int SetMyCity_MSGNO=0X231003;
    public static final int GetRoundList_MSGNO=0X231024;
    public static final int GetClearMsg_MSGNO = 0X231025;

    public static final int SendRound_MSGNO = 0X11001;
    public static final int SendSys_MSGNO = 0X11002;
    public static final int BindPushUid_MSGNO = 0X11087;
    public static final int ChangeServState_MSGNO = 0X11080;

    public static final int GetComplaintList_MSGNO = 0X11081;

    public static final int GetMyOrderList_MSGNO = 0X11271;
    public static final int GetInviList_MSGNO = 0X11273;


    public static final int AddFeedbak_MSGNO=0X1605;
    public static final int CheckUsername_MSGNO=0X1606;
    public static final int CheckMobile_MSGNO=0X2804;
    public static final int UserInfo_MSGNO=0X2804;
    public static final int Register_MSGNO=0X2805;
    public static final int CommentRound_MSGNO=0X2806;
    public static final int CommentOrder_MSGNO=0X2807;
    public static final int CommentZan_MSGNO = 0X2808;

    public static final int GetHomePhotoList_MSGNO = 0X16001;
    public static final int GetHomePhoto_MSGNO = 0X16004;

    public static final int GetStartIMG_MSGNO = 0X160112;
    public static final int GetTopIMG_MSGNO = 0X160113;
    public static final int GetBottomIMG_MSGNO = 0X160122;
    public static final int GetTopAticle_MSGNO=0X160202;

    public static final int AddPhoto_MSGNO = 0X20001;
    public static final int AddBoomnews_MSGNO = 0X200002;

    public static final int AddConsult_MSGNO = 0X200027;
    public static final int SaveServiceinfo_MSGNO = 0X200052;

    public static final int SaveOrder_MSGNO = 0X200082;

    public static final int GetConfigINFO_MSGNO=0X012012;
    public static final int GetCateGory_MSGNO=0X02051;

    public static final int GetArticalList_MSGNO =0X02057;
    public static final int GetDoctorList_MSGNO =0X02058;
    public static final int GetConsult_MSGNO=0X020034;
    public static final int GetConsultDetails_MSGNO=0X020022;


    public static final int GetFansList_MSGNO =0X02061;
    public static final int Authentication_MSGNO =0X02062;
    public static final int SendMsg_MSGNO =0X02063;
    public static final int GetOrderDateListMsg_MSGNO =0X02064;
    public static final int GetUserAuthinfoMsg_MSGNO =0X02065;
    public static final int GetFriendListMsg_MSGNO =0X02066;
    public static final int EptSevPhotoMsg_MSGNO =0X02067;
    public static final int GetAlipayCodeMsg_MSGNO= 0X02068;

    public static final int SaveAlipayCodeMsg_MSGNO= 0X02069;
    public static final int SaveUserUserPrivacyMsg_MSGNO =0x02070;
    public static final int DeleteMessageByIdReq_MSGNO=0x02071;
    public static final int ForPwdGetCodeReq_MSGNO =0x02072;
    public static final int ForPwdReq_MSGNO =0x02073;
    public static final int ReFreshList_MSGNO =0x02074;
    public static final int ShowServAd_MSGNO =0x02075;
    public static final int UploadingCount_MSGNO =0x02076;
    public static final int GetDistrict_MSGNO =0x02077;
    public static final int GetCityHomeCate_MSGNO =0x02078;
    public static final int GetHomeStartServerList_MSGNO =0x02079;
    public static final int GetUnReadMsgCount_MSGNO =0x02080;
    public static final int GetBoardServerList_MSGNO =0x02081;


    public static final int GetWXPayParam_MSGNO=0x02081;


}
