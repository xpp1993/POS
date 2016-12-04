package com.lxkj.xpp.mvpdemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.xpp.mvpdemo.adapter.MenueAdapter;
import com.lxkj.xpp.mvpdemo.beans.MenuItem;
import com.lxkj.xpp.mvpdemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActionBarActivity {
    protected FragmentManager mFragemntManager;//Fragment管理器
    Fragment mArticleFragment = new ArticleListFragment();//文章列表fragment
    Fragment mAboutFragment;//关于fragment
    private DrawerLayout mDrawerLayout;//除了actionBar之外的根视图
    private RecyclerView mMenuRecyclerView;//菜单RecyclerView
    private ActionBarDrawerToggle mDrawerToggle;//ActionBar的菜单开关

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidgets() {
        mFragemntManager=getFragmentManager();
        setupDrawerToogle();
        setupMenuRecyclerView();
        //显示文章列表的Fragment
        addFragemnt(mArticleFragment);
       // super.initWidgets();
    }

    private void setupDrawerToogle() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupMenuRecyclerView() {
        mMenuRecyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MenueAdapter menueAdapter = new MenueAdapter();
        menueAdapter.addItems(prepareMenuItems());
        menueAdapter.setOnItemClickListener(new OnItemClickListener<MenuItem>() {
            @Override
            public void onClick(MenuItem item) {
                clickMenuItem(item);
            }
        });
        mMenuRecyclerView.setAdapter(menueAdapter);
    }

    private List<MenuItem> prepareMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(getString(R.string.article), R.drawable.home));
        menuItems.add(new MenuItem(getString(R.string.about_menu), R.drawable.about));
        menuItems.add(new MenuItem(getString(R.string.exit), R.drawable.exit));
        return menuItems;
    }

    private void clickMenuItem(MenuItem item) {
        mDrawerLayout.closeDrawers();
        switch (item.iconResId) {
            case R.drawable.home:
                replaceFragment(mArticleFragment);
                break;
            case R.drawable.about:
                replaceFragment(mAboutFragment);
                break;
            case R.drawable.exit:
                isQuit();
                break;
        }
    }

    protected void addFragemnt(Fragment fragment) {
        mFragemntManager.beginTransaction().add(R.id.articles_container, fragment).commit();
    }

    protected void replaceFragment(Fragment fragment) {
        mFragemntManager.beginTransaction().replace(R.id.articles_container, fragment).commit();
    }

    private void isQuit() {
        new AlertDialog.Builder(this).setTitle("确定退出？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).create().show();
    }
}
