package com.cores.widget.refreshlist;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by wei on 16-3-31.
 */
public class MGridLayoutManager extends GridLayoutManager {

    public MGridLayoutManager(Context context, final int spanCount, final BaseLoadMoreRecyclerAdapter mAdapter) {
        super(context, spanCount);
        setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case BaseLoadMoreRecyclerAdapter.TYPE_FOOTER:
                        return spanCount;
                    case BaseLoadMoreRecyclerAdapter.TYPE_ITEM:
                        return 1;
                    default:
                        return -1;
                }
            }

        });
    }


}
