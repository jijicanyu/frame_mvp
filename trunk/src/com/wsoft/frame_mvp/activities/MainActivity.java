package com.wsoft.frame_mvp.activities;

import com.wsoft.frame_mvp.events.ItemSelectedEvent;
import com.wsoft.frame_mvp.fragments.IpsumDetailFragment;
import com.wsoft.frame_mvp.fragments.IpsumListFragment;
import com.wsoft.frame_mvp.vus.MainVu;


public class MainActivity extends BasePresenterActivity<MainVu> {

    @Override
    protected void onBindVu() {
        fm.beginTransaction()
                .replace(vu.getContainerId(), IpsumListFragment.newInstance())
                .commit();
    }

    @Override
    protected void afterResume() {
        bus.registerSticky(this);
    }

    @Override
    protected void beforePause() {
        bus.unregister(this);
    }

    @Override
    public boolean handleBackPressed() {
        bus.removeAllStickyEvents();
        return false;
    }

    @Override
    protected Class<MainVu> getVuClass() {
        return MainVu.class;
    }

    public void onEvent(ItemSelectedEvent event){
        fm.beginTransaction()
                .replace(vu.getContainerId(), IpsumDetailFragment.newInstance())
                .addToBackStack(IpsumDetailFragment.class.getName())
                .commit();
    }

}
