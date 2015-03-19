package com.base.config;

/**
 * Created by Administrator on 2014/11/8.
 */
public class GlobalVariable {
    static GlobalVariable mySelf = new GlobalVariable();

    private GlobalVariable(){

    }
    public static GlobalVariable getInstance(){
        return mySelf;
    }
    boolean flag = false;

    public boolean getFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
