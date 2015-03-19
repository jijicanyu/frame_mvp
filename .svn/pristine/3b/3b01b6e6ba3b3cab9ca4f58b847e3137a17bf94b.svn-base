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

public class MessageFragment extends Fragment implements View.OnClickListener {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.message_layout,
				container, false);
        ViewUtils.inject(this,messageLayout);
		return messageLayout;
	}


    @OnClick({ R.id.msg_img })
    @Override
    public void onClick(View view) {
        //Toast.makeText(getActivity(),"taost",Toast.LENGTH_SHORT).show();
       ((MainActivity)getActivity()).MsgFragmentClick(view.getId());
    }
}
