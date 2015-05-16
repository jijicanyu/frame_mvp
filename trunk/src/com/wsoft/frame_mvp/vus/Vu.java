package com.wsoft.frame_mvp.vus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wei on 3/10/15.
 */
public interface Vu {
    void init(LayoutInflater inflater, ViewGroup container);
    View getView();
}
