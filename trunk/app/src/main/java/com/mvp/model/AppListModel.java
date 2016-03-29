package com.mvp.model;

/**
 * Created by aa on 2015/7/2.
 */
public class AppListModel {

    public String soft_sn;//	应用编码（与接口交互用）
    public String soft_name;//名称
    public int soft_type;//类型id
    public String soft_type_des;//类型名
    public String soft_size;//大小
    public int soft_gold_num;//	获得金币数
    public int soft_jp_flag;//		是否推荐1-非推荐,2-推荐
    public String soft_logo;//	string	Logo地址
    public String soft_down_url;//	string	下载地址
    public String soft_short_des;//	string	短描述
    public int soft_download_num;//下载次数
    public String soft_package_name;//	string packgename
    public String soft_down_url_neiwang;//string 内网地址
    public int is_jh_flag=0;//是否已经激活

}
