package com.cores.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cores.mode.ItemInfo;
import com.mvp.R;

import java.util.ArrayList;

/**
 * Created by aa on 2015/3/5.
 */
public class ItemAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<ItemInfo> datalist=new ArrayList<ItemInfo>();


    public ItemAdapter(Context act, ArrayList<ItemInfo> list)
    {
        layoutInflater=LayoutInflater.from(act);
        datalist=list;
    }

    @Override
    public int getCount() {
        return datalist == null ? 0 : datalist.size();
    }

    @Override
    public Object getItem(int i) {
        return datalist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder _Holder = null;
        if(view==null)
        {
            _Holder=new Holder();
            view=layoutInflater.inflate(R.layout.item_refreshlist,null);
            _Holder.txt=(TextView)view.findViewById(R.id.txt);
            view.setTag(_Holder);
        }
        else {
            _Holder = (Holder) view.getTag();
        }
        _Holder.txt.setText(""+datalist.get(i).name);

        return view;
    }

    class Holder
    {
        TextView txt;
    }
}
