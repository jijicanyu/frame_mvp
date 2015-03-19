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

public class ContactsFragment extends Fragment implements View.OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.contacts_layout,
				container, false);
        ViewUtils.inject(this, contactsLayout); //注入view和事件
		return contactsLayout;
	}

    @OnClick({ R.id.cont_img })
    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).ContacFragmentClick(view.getId());
    }


}
