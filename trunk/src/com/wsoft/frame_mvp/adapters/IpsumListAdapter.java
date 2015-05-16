package com.wsoft.frame_mvp.adapters;

import com.wsoft.frame_mvp.data.Ipsum;
import com.wsoft.frame_mvp.vus.IpsumListItemVu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 3/10/15.
 */
public class IpsumListAdapter extends BasePresenterAdapter<IpsumListItemVu> {

    List<String> titles = new ArrayList<String>(Ipsum.VALUES_MAP.keySet());

    @Override
    protected void onBindListItemVu(int position) {
        String title = titles.get(position);
        vu.setTitle(title);
    }

    @Override
    protected Class<IpsumListItemVu> getVuClass() {
        return IpsumListItemVu.class;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getTitle(int position) {
        return (String) getItem(position);
    }

}
