package com.cores.widget.refreshlist;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.mvp.R;

import java.util.List;

/**
 * 多个开源项目各取优点缺点
 * <p/>
 * 控件需要绑定 PullLoadMoreListener 来进行下拉刷新上拉加载的监听
 * <p/>
 * 可以用setRefresh（）方法手动开启和关闭顶部刷新动画
 * 可以用setHasFooter（）方法显示和关闭底部加载动画
 * 以上两个方法可以用一个方法stopAnim（）代替
 */
public class PullLoadMoreRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private PullLoadMoreListener mPullLoadMoreListener;

    protected BaseLoadMoreRecyclerAdapter mAdapter;

    OnRecycleViewScrollListener recyclerScrollLisen;


    public PullLoadMoreRecyclerView(Context context) {

        super(context);
        initView(context);
    }

    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {

        LayoutInflater.from(context).inflate(R.layout.pull_loadmore_layout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.darkred);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(recyclerScrollLisen = new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {

                onLoad();
            }
        });

    }


    private void onLoad() {

        if (!(mSwipeRefreshLayout.isRefreshing())) {
            Log.e("XXXXX", "setLoading ture ");


            //执行lisener
            if (mPullLoadMoreListener != null) {
                mAdapter.setHasFooter(true);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                mPullLoadMoreListener.onLoadMore();
            }

        } else {

        }
    }

    @Override
    public void onRefresh() {

        if (mAdapter == null) {
            //执行lisener
            if (mPullLoadMoreListener != null) {
                setRefreshEnable(false);
                mPullLoadMoreListener.onRefresh();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        } else {
            /*
            if (mAdapter.hasFooter()) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
            */
                //执行lisener
                if (mPullLoadMoreListener != null) {
                    setRefreshEnable(false);
                    mPullLoadMoreListener.onRefresh();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            //}
        }
    }


    //
    public void showMoreData(final List dataList) {

        if (dataList == null) {
            stopAnim();
            return;
        }
        if (dataList.isEmpty()) {
            mAdapter.setHasMoreData(false);
        } else {
            mAdapter.appendToList(dataList);
            mAdapter.setHasMoreData(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter(BaseLoadMoreRecyclerAdapter mAdapter) {

        this.mAdapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
    }


    //LinearLayoutManager
    public void setLinearLayout(BaseLoadMoreRecyclerAdapter adapter) {

        if (mAdapter == null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }


    // GridLayoutManager
    public void setGridLayout(BaseLoadMoreRecyclerAdapter adapter, int spanCount) {

        if (mAdapter == null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(mAdapter);
            MGridLayoutManager gridLayoutManager = new MGridLayoutManager(getContext(), spanCount, mAdapter);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
    }


    //StaggeredGridLayoutManager  目前这个控件瀑布流无法完成加载中单独起一行的功能
    public void setStaggeredGridLayout(BaseLoadMoreRecyclerAdapter adapter, int spanCount) {

        if (mAdapter == null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(mAdapter);
            StaggeredGridLayoutManager staggeredGridLayoutManager =
                    new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
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

    //禁用下拉刷新的功能
    public void setRefreshEnable(boolean enable) {

        setEnabled(enable);
    }


    //禁用上拉加载的功能  这个方法没仔细测试过 谨慎使用
    public void setLoadingEnable(boolean enable) {

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

    /**
     * 只clear数据 不执行adapter 得notify方法
     */
    public void clearData() {

        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    /**
     * 清楚数据 并让Adapter notify一下
     */
    public void clearAndNotify() {

        if (mAdapter != null) {
            mAdapter.clearAndNotify();
        }
    }


    /**
     * 用于判断是横向滑动还是纵向滑动的 case  避免横滑成为竖滑
     *
     * @param ev
     * @return
     */
    private float xDistance, yDistance, xLast, yLast;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 如果当前正在刷新，就停止刷新，如果当前正在Loading就关闭Loading
     */
    public void stopAnim() {

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mAdapter.hasFooter()) {
            mAdapter.setHasFooter(false);
        }
    }

    public void setHasMore(boolean hasMore) {
        mAdapter.setHasMoreData(hasMore);
    }

    public boolean isHasMore()
    {
        return  mAdapter.hasMoreData();
    }
}
