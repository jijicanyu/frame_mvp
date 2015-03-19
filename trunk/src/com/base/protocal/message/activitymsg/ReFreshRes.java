package com.base.protocal.message.activitymsg;

import com.base.protocal.BaseJsonResponseMsg;

/**
 * 用于刷新的Response  用于传递刷新列表的消息
 */
public class ReFreshRes extends BaseJsonResponseMsg {
    public ReFreshRes()
    {
        setMsgno(ReFreshList_MSGNO);
    }
}
