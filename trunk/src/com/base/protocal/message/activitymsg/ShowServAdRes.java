package com.base.protocal.message.activitymsg;


import com.base.protocal.BaseJsonResponseMsg;

/**
 * 显示服务者活动图片的消息
 */
public class ShowServAdRes extends BaseJsonResponseMsg {
    public ShowServAdRes()
    {
        setMsgno(ShowServAd_MSGNO);
    }
}
