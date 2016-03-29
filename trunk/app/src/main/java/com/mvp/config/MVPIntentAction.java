package com.mvp.config;

import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by weizongwei on 15-12-1.
 * 页面之间Intent 传递数据的key 还有广播的key 都写在这个页面  广播尽量用Localbrodercast
 */
public class MVPIntentAction {
    public static final String REFRESHMEMBERLIST="com.mvp.refreshmemberlist";

    public static final String COUNTY_ISOK="com.mvp.COUNTY_ISOK";

    public static final String MY_BUY_ORDER="com.mvp.MY_BUY_ORDER";

    public static final String MEMBER_MOBILE_LIST="member_mobile_list";

    public static final String INTENT_MEMBER_MODEL="member_model";
    public  static final String INTENT_GIFT_MODEL="gift_model";
    public static final String INTENT_DEL_INDEX="del_idnex";
    public  static final String INTENT_COUNTY_MODEL ="county_model";
    public static final String INTENT_COUNT_LIST="intent_count_list";


    public static final String INTENT_GOTO_SCAN="pos_shop_gotoscan";
    public static final String INTENT_MY_BUY="my_buy";
    public static final String INTENT_PAY_TYPE="pay_type";
    public static final String INTENT_WULIU_TYPE="wuliu_type";
    public static final String INTENT_OTO_TYPE="oto_type";

    public  static final String INTENT_IS_MODIFY="ismodify";

    public  static final String INTENT_ID="intent_id";
    public  static final String INTENT_MODEL="intent_model";
    public static final String INTENT_LIST="intent_list";

    public static   final String INTENT_PRUCHASEITEM_ID="intent_pruchaseitem_id";
    public static final String PROVIDER_KEY="provider_model";
    public static final String PROVIDER_FROM="provider_from";
    public static final String SERVICE_ITEM_FROM="service_item";
    public static final String SERVICE_ITEM_POSITION_FROM="service_item_position";


    public static final String MEMBER_OPTION_TYPE="member_option_type";
    public static final String BROAD_ADD_GIFT="com.mvp.broad_add_gift";
    public static final String BROAD_DELETE_GIFT="com.mvp.broad_del_gift";
    public static final String BROAD_ADD_PAYOFF="com.mvp.broad_add_payoff";
    public static final String BROAD_DELETE_PAYOFF="com.mvp.broad_del_payoff";
    public static  final String BROAD_ADD_LUCK="com.mvp.broad_add_luck";
    public static final String MEMBER_SEL_COUNT="member_sel_count";
    public static final  String BROAD_ADD_LEVEL="com.mvp.broad_add_leve";
    public static final String BROAD_ADD_EXCHANGERULE="com.mvp.broad_add_exchangerule";
    public static final String BROAD_ADD_TAG="com.mvp.broad_add_tag";
    public static final String BROAD_PAY_RESULT="com.mvp.broad_pos_payresult";
    public static final String BROAD_FINISH="com.mvp.broad_finish";
    public static final String BROAD_REMOVE_LIST="intent_remove_list";
    public static final String BROAD_ADD_EXPEND="intent_add_expend";
    public static final String BROAD_SEL_TYPE="com.mvp.broad_seltype";
    public static final String BROAD_REFRESH="com.mvp.refresh";
    public static final String INDEX="dsadidnex";


    public class MemOPT_TYPE
    {
        public  static final int TYPE_RECHAGE=0;
        public  static final int TYPE_EXCHANGE=1;
        public  static final int TYPE_SERVICE=2;
    }
}
