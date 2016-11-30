package com.lxkj.xpp.jtm_techfrontier;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.xpp.jtm_techfrontier.adapter.MenuAdapter;
import com.lxkj.xpp.jtm_techfrontier.bean.MenuItem;
import com.lxkj.xpp.jtm_techfrontier.utils.BaseActionBarActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActionBarActivity {
    protected FragmentManager mFragmentManager;//Fragment管理器
    Fragment mArticleFragment = new ArticleListFragment();//文章列表Fragm
    Fragment mAboutFragment;//关于Fragment
    private DrawerLayout mDrawerLayout;//除了Actionbar以外的根视图
    private RecyclerView mMenuRecyclerView;//菜单RecyclerView
    // protected Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;//Actionbar上的菜单开关

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initWidgets() {
        mFragmentManager = getFragmentManager();
        setupDrawerToggle();
        setupMenuRecyclerView();
        // 显示文章列表Fragment
        addFragment(mArticleFragment);
    }
    private void setupDrawerToggle() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void setupMenuRecyclerView() {
        mMenuRecyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // 初始化菜单Adapter
        MenuAdapter menuAdapter = new MenuAdapter();
        menuAdapter.addItems(prepareMenuItems());
        menuAdapter.setOnItemClickListener(new OnItemClickListener<MenuItem>() {
            @Override
            public void onClick(MenuItem item) {
                clickMenuItem(item);
            }
        });
        mMenuRecyclerView.setAdapter(menuAdapter);
    }
    private List<MenuItem> prepareMenuItems() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(new MenuItem(getString(R.string.article), R.drawable.home));
        menuItems.add(new MenuItem(getString(R.string.about_menu), R.drawable.about));
        menuItems.add(new MenuItem(getString(R.string.exit), R.drawable.exit));
        return menuItems;
    }
    private void clickMenuItem(MenuItem item) {
        mDrawerLayout.closeDrawers();
        switch (item.iconResId) {
            case R.drawable.home: // 全部
                replaceFragment(mArticleFragment);
                break;

            case R.drawable.about: // 招聘信息
                if (mAboutFragment == null) {
                    mAboutFragment = new AboutFragement();
                }
                replaceFragment(mAboutFragment);
                break;

            case R.drawable.exit: // 退出
                isQuit();
                break;

            default:
                break;
        }
    }

    protected void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.articles_container, fragment).commit();
    }

    protected void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.articles_container, fragment).commit();
    }

    private void isQuit() {
        new AlertDialog.Builder(this)
                .setTitle("确认退出?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).create().show();
    }
}
