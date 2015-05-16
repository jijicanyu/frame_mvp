package com.wsoft.frame_mvp.vus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wsoft.frame_mvp.R;
import com.wsoft.frame_mvp.data.Ipsum;

/**
 * Created by wei on 3/10/15.
 */
public class IpsumDetailVu implements Vu {

    View view;
    TextView titleView;
    TextView bodyView;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.ipsum_detail, container, false);
        titleView = (TextView) view.findViewById(R.id.title);
        bodyView = (TextView) view.findViewById(R.id.body);
    }

    @Override
    public View getView() {
        return view;
    }

    public void setIpsum(Ipsum ipsum){
        titleView.setText(ipsum.title);
        bodyView.setText(ipsum.body);
    }

}
