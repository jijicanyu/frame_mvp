package com.mvp.presenter;

import android.os.Bundle;
import android.os.Message;
import com.base.presenter.BaseCommPresenter;
import com.cores.message.MainRequest;
import com.cores.message.MainResponse;
import com.mvp.iview.IMainView;

/**
 * Created by aa on 2015/6/23.
 */
public class MainPresenter extends BaseCommPresenter<IMainView> {

    //请求
    private static final int REQ_GETAPPLIST_MSG = 0x002123;
    private static final int RES_GETAPPLIST_MSG = 0x002124;

    private static final String SOFT_JP_FLAG = "soft_jp_flag";
    private static final String CURRENT_PAGE = "current_page";
    private static final String PAGE = "page_size";
    private static final String SOFT_TYPE = "soft_type";


    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case REQ_GETAPPLIST_MSG: {

                MainRequest req = new MainRequest();


                req.put(SOFT_JP_FLAG, 2 + "");
                req.put(SOFT_TYPE, 1 + "");
                req.put("soft_jh_type", 0 + "");

                req.put(CURRENT_PAGE, 1 + "");
                req.put(PAGE, 10 + "");

                req.setResPonseMsgWhat(RES_GETAPPLIST_MSG);

                sendHttpPost(req, MainResponse.class);
                iView.showProgressBar();

            }
            break;
            case RES_GETAPPLIST_MSG:
            {
                iView.hideProgressBar();


            }
            break;
        }
    }

    @Override
    public void initData(Bundle saveInstnce) {
        getHandler().sendEmptyMessage(REQ_GETAPPLIST_MSG);
    }


}
