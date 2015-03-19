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

public class SettingFragment extends Fragment implements View.OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.setting_layout,
				container, false);
        ViewUtils.inject(this, settingLayout);
		return settingLayout;
	}

    @OnClick({R.id.set_img})
    @Override
    public void onClick(View view) {
        //Toast.makeText(getActivity(),"taost",Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).SetFragmentClick(view.getId());
    }
}
