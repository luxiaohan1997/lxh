package com.jy.day03;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRl;
    private TabLayout mTab;
    /**
     * 首页
     */
    private TextView mToolname;
    private Toolbar mTool;
    private FragmentManager fm;
    private HomeFragment f1;
    private UploadFragment f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initTab();
    }

    private void initTab() {
        mTab.addTab(mTab.newTab().setText("首页"));
        mTab.addTab(mTab.newTab().setText("上传"));

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mToolname.setText("首页");
                        FragmentTransaction bt1 = fm.beginTransaction();
                        bt1.show(f1);
                        bt1.hide(f2);
                        bt1.commit();
                        break;
                    case 1:
                        mToolname.setText("上传");
                        FragmentTransaction bt2 = fm.beginTransaction();
                        bt2.show(f2);
                        bt2.hide(f1);
                        bt2.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction bt = fm.beginTransaction();
        f1 = new HomeFragment();
        f2 = new UploadFragment();
        bt.add(R.id.rl,f1);
        bt.add(R.id.rl,f2);
        bt.hide(f2);
        bt.commit();
    }

    private void initView() {
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mTab = (TabLayout) findViewById(R.id.tab);
        mToolname = (TextView) findViewById(R.id.toolname);
        mTool = (Toolbar) findViewById(R.id.tool);
    }
}
