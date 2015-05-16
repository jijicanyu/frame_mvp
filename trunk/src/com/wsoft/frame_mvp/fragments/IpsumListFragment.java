package com.wsoft.frame_mvp.fragments;

import com.wsoft.frame_mvp.adapters.IpsumListAdapter;
import com.wsoft.frame_mvp.events.ItemSelectedEvent;
import com.wsoft.frame_mvp.vus.IpsumListVu;
import com.wsoft.frame_mvp.vus.VuCallback;

/**
 * Created by wei on 3/10/15.
 */
public class IpsumListFragment extends BasePresenterFragment<IpsumListVu> {

    IpsumListAdapter adapter = new IpsumListAdapter();
    VuCallback<Integer> selectCallback = new VuCallback<Integer>() {
        @Override
        public void execute(Integer result) {
            bus.postSticky(new ItemSelectedEvent(adapter.getTitle(result)));
        }
    };

    @Override
    protected void onBindVu() {
        vu.setListAdapter(adapter);
        vu.setSelectCallback(selectCallback);
    }

    @Override
    protected Class<IpsumListVu> getVuClass() {
        return IpsumListVu.class;
    }

    public static IpsumListFragment newInstance(){
        return new IpsumListFragment();
    }

}
