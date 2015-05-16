package com.wsoft.frame_mvp.fragments;

import com.wsoft.frame_mvp.data.Ipsum;
import com.wsoft.frame_mvp.events.ItemSelectedEvent;
import com.wsoft.frame_mvp.vus.IpsumDetailVu;

import java.util.Map;

/**
 * Created by wei on 3/10/15.
 */
public class IpsumDetailFragment extends BasePresenterFragment<IpsumDetailVu> {

    Map<String, Ipsum> ipsumMap = Ipsum.VALUES_MAP;

    @Override
    public void afterResume() {
        bus.registerSticky(this);
    }

    @Override
    public void beforePause() {
        bus.unregister(this);
    }

    @Override
    protected Class<IpsumDetailVu> getVuClass() {
        return IpsumDetailVu.class;
    }

    public void onEvent(ItemSelectedEvent event){
        vu.setIpsum(ipsumMap.get(event.title));
    }

    public static IpsumDetailFragment newInstance(){
        return new IpsumDetailFragment();
    }

}
