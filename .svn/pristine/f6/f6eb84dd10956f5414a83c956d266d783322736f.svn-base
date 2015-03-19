package com.mvp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mvp.R;
import com.mvp.view.MainActivity;

public class NewsFragment extends Fragment implements View.OnClickListener{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.news_layout, container,
				false);

        ViewUtils.inject(this,newsLayout);
        //newsLayout.findViewById(R.id.act_img).setOnClickListener(this);
		return newsLayout;
	}


    @OnClick({R.id.act_img})
    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).NewsFragmentClick(view.getId());
    }
}
