package com.wsoft.frame_mvp.vus;

import com.wsoft.frame_mvp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by wei on 3/10/15.
 */
public class MainVu implements Vu {

    View view;
    FrameLayout containerView;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        containerView = (FrameLayout) view.findViewById(R.id.container);
    }

    @Override
    public View getView() {
        return view;
    }

    public int getContainerId(){
        return containerView.getId();
    }

}
