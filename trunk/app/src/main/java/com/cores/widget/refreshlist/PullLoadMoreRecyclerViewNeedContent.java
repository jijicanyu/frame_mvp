package com.cores.widget.refreshlist;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import com.mvp.R;

import java.util.List;

/**
 * 作为PullLoadMoreRecyclerView的扩展控件
 * 要求用这个控件时 必须网往这个控件内部嵌入一个Recyclerview  为了方便 在xml里配置recyclerview的一些属性
 */
public class PullLoadMoreRecyclerViewNeedContent extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private PullLoadMoreListener mPullLoadMoreListener;

    protected BaseLoadMoreRecyclerAdapter mAdapter;
    OnRecycleViewScrollListener recyclerScrollLisen;

    //一些参数，解决因为过早setAdapter而recyclerview还为空的问题
    private static final int TYPE_LINEARLAY = 0, TYPE_GRIDLAY = 1, TYPE_STAGLAY = 2;
    int layouttype = 0;
    int spanCount = 1;


    public PullLoadMoreRecyclerViewNeedContent(Context context) {

        super(context);
        initView();
    }

    public PullLoadMoreRecyclerViewNeedContent(Context context, AttributeSet attrs) {

        super(context, attrs);
        initView();
    }


    private void initView() {

        setOnRefreshListener(this);
        setColorSchemeResources(R.color.darkred);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 获取RecyclerView对象
     */
    private boolean initRecyclerView() {

        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) childView;

                mRecyclerView.setVerticalScrollBarEnabled(true);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addOnScrollListener(recyclerScrollLisen = new OnRecycleViewScrollListener() {
                    @Override
                    public void onLoadMore() {

                        onLoad();
                    }
                });

                if (mAdapter != null)//说明在onLayout之前就已经setAdapter了
                {

                    if (layouttype == TYPE_GRIDLAY) {
                        setGridLayout(mAdapter, spanCount);
                    } else if (layouttype == TYPE_STAGLAY) {
                        setStaggeredGridLayout(mAdapter, spanCount);
                    } else {
                        setLinearLayout(mAdapter);
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }


    private void onLoad() {

        if (!(isRefreshing())) {

            //执行lisener
            if (mPullLoadMoreListener != null) {
                mAdapter.setHasFooter(true);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                setRefreshing(false);
                mPullLoadMoreListener.onLoadMore();
            }

        } else {

        }
    }

    @Override
    public void onRefresh() {

        if (mAdapter.hasFooter()) {

            //执行lisener
            if (mPullLoadMoreListener != null) {
                setRefreshEnable(false);
                mPullLoadMoreListener.onRefresh();
            } else {
                setRefreshing(false);
            }
        } else {
            setRefreshing(false);
        }
    }


    public void showMoreData(final List dataList) {

        if (dataList == null) {
            mAdapter.setHasMoreAndFooter(true, false);
            return;
        }

        if (dataList.isEmpty()) {
            mAdapter.setHasMoreAndFooter(false, false);
        } else {
            mAdapter.appendToList(dataList);
            mAdapter.setHasMoreAndFooter(true, false);
        }
        mAdapter.notifyDataSetChanged();

    }

    public void setAdapter(BaseLoadMoreRecyclerAdapter mAdapter) {

        this.mAdapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
    }


    //LinearLayoutManager
    public void setLinearLayout(BaseLoadMoreRecyclerAdapter adapter) {

        if (adapter != null) {
            mAdapter = adapter;
            layouttype=TYPE_LINEARLAY;

            if(mRecyclerView!=null) {

                mRecyclerView.setAdapter(mAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
            }
        }
    }


    // GridLayoutManager
    public void setGridLayout(BaseLoadMoreRecyclerAdapter adapter, int spanCount) {

        if (adapter != null) {
            mAdapter = adapter;
            layouttype=TYPE_GRIDLAY;
            this.spanCount=spanCount;

            if (mRecyclerView != null) {
                mRecyclerView.setAdapter(mAdapter);
                MGridLayoutManager gridLayoutManager = new MGridLayoutManager(getContext(), spanCount, mAdapter);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(gridLayoutManager);
            }

        }
    }


    //StaggeredGridLayoutManager  目前这个控件瀑布流无法完成加载中单独起一行的功能
    public void setStaggeredGridLayout(BaseLoadMoreRecyclerAdapter adapter, int spanCount) {

        if (adapter != null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(mAdapter);

            layouttype=TYPE_GRIDLAY;
            this.spanCount=spanCount;

            if (mRecyclerView != null) {
                StaggeredGridLayoutManager staggeredGridLayoutManager =
                        new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            }
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {

        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {

        return mRecyclerView;
    }

    public void scrollToTop() {

        mRecyclerView.scrollToPosition(0);
    }

    //改为private 禁用此方法
    private void setAdapter(RecyclerView.Adapter adapter) {

        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setRefreshEnable(boolean enable) {

        setEnabled(enable);
    }


    public void setLoadingEnable(boolean enable) {

        if(mRecyclerView==null)
            return;

        if (enable) {
            if (recyclerScrollLisen == null)
                mRecyclerView.addOnScrollListener(recyclerScrollLisen = new OnRecycleViewScrollListener() {
                    @Override
                    public void onLoadMore() {

                        onLoad();
                    }
                });
        } else {
            mRecyclerView.removeOnScrollListener(recyclerScrollLisen);
            recyclerScrollLisen = null;
        }
    }

    public void setHasFooter(boolean hasFooter) {

        mAdapter.setHasFooter(hasFooter);
    }

    public void setPullLoadMoreListener(PullLoadMoreListener listener) {

        this.mPullLoadMoreListener = listener;
    }

    public void clearData() {

        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

}
