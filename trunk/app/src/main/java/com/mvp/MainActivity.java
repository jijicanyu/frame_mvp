package com.mvp;

import android.view.View;
import com.base.activity.BaseCommActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mvp.iview.IMainView;
import com.mvp.presenter.MainPresenter;

/**
 * Created by aa on 2015/6/23.
 */
public class MainActivity extends BaseCommActivity<MainPresenter> implements IMainView
{

    @ViewInject(R.id.lay_main)
    View lay_main;

    @Override
    protected Class<MainPresenter> getPsClass() {
        return MainPresenter.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initAllWidget() {
        lay_main.setOnClickListener(this);
    }



    @Override
    protected void clickView(View v) {
        switch(v.getId()) {
            case R.id.lay_main: {
                //showMsg();
            }
            break;
        }
    }

    @Override
    public void showMsg() {
        //测试
        toast("is showmsg methon");
    }
}
