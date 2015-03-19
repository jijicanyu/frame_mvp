package com.mvp.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.base.widget.RefreshListView;
import com.base.widget.adapter.ItemAdapter;
import com.base.widget.mode.ItemInfo;
import com.mvp.R;

import java.util.ArrayList;

public class RefreshListDemoActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    private String[] drawerListViewItems={"1","2","3","4","5","6"};
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    /**
     * 给ListView添加下拉刷新
     */
    private RefreshListView swipeLayout;



    /**
     * ListView适配器
     */
    private ItemAdapter adapter;
    private ArrayList<ItemInfo> infoList=new ArrayList<ItemInfo>();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdemo);

        initRefreshLayout();

        initDrawerLayout();

    }

    private void initDrawerLayout()
    {
        // get ListView defined in activity_listdemo.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));

        // 2. App Icon
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 2.1 create ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );

        // 2.2 Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        // 2.3 enable and show "up" arrow
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // just styling option
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(RefreshListDemoActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);

        }
    }


    /**
     * 实例化RefreshLayout和显示初始布局
     */
    private  void initRefreshLayout()
    {
        swipeLayout = (RefreshListView) this.findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(this);
        // 顶部刷新的样式
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light,android.R.color.holo_blue_bright);
        swipeLayout.setOnLoadListener(new RefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {

                swipeLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        ItemInfo info = new ItemInfo();
                        info.name="上拉获取更多";
                        infoList.add(info);
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        swipeLayout.setLoading(false);
                    }
                }, 1000);
            }
        });


        swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                infoList = new ArrayList<ItemInfo>();
                ItemInfo info = new ItemInfo();
                info.name="coin";
                infoList.add(info);
                infoList.add(info);
                adapter = new ItemAdapter(RefreshListDemoActivity.this, infoList);
                swipeLayout.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);

            }
        },2000);

    }

    //SwipeRefreshLayout的准备刷新的操作
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeLayout.setRefreshing(false);
                // 更新完后调用该方法结束刷新
                ItemInfo info = new ItemInfo();
                info.name="下拉刷新";
                infoList.add(info);
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}