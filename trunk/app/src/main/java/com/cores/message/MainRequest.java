package com.cores.message;

import com.base.protocal.http.RequestMsg;

/**
 * Created by aa on 2015/7/2.
 */
public class MainRequest extends RequestMsg {
    @Override
    public String getActionName() {
        return "soft_list";
    }
}
