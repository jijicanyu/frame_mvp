package com.base.config;

/*
* 频道类
* 用于各种状态值的配置类
*
* */
public class ChannelConfig {
    /*消息的channel
    *       type : 0系统消息1订单消息  2用户私信
    */
    public static  final int sys_msg=0,order_msg=1,user_msg=2;


    // 前往注册页面的requestcode
    public static final int REGISER_REQCODE=0x000214;
    // 注册成功后,页面返回的resultcode
    public static final int REGISER_RESCODE=0x000213;

    public static final String user_nickname="nickname";
    public static final String user_password ="password";

    //是否需要跳转到消息列表
    public static boolean need_goto_MsgList=false;

}
