package com.example.user.myapplication;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Main2Activity extends AppCompatActivity {
    /**
     * 將{@link android.support.v4.view.PagerAdapter}提供給
     * {@link FragmentPagerAdapter}派生,保存每一個加載片段
     * 並切換至{@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * {@link ViewPager}為存放內容
     */
    public ViewPager mViewPager;

    public String id,intent_name;
    public int intent_permissions;
    public Bundle bundle;
    public TableLayout data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        intent_permissions = intent.getIntExtra("permissions",0);
        intent_name = intent.getStringExtra("name");
        bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",intent_name);
        //取得toolbar的實體
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //使用setSupportActionBar設定將Toolbar取代原本的actionbar
        setSupportActionBar(toolbar);
        //創造回傳三個分頁的adapter
        //主要的activity
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //設定mViewPager的adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //取得tabLayout的實體
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //將menu添加到操作欄
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 處理點擊操作欄發生的事件
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.log_out){
            //初始化Intent物件
            Intent intent = new Intent();
            //從Main2Activity 到MainActivity
            intent.setClass(Main2Activity.this,MainActivity.class);
            //開啟Activity
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * {@link FragmentPagerAdapter} 將回傳與之對應的畫面
     * 其中的sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    personal tab1 = new personal();
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    clock_on tab2 = new clock_on();
                    tab2.setArguments(bundle);
                    return tab2;
                case 2:
                    manage tab3 = new manage();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            //顯示出3個分頁
            int count;
            if(intent_permissions==0){
                count=2;
            }
            else{
                count=3;
            }
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "個資";
                case 1:
                    return "打卡";
                case 2:
                    return "管理";
            }
            return null;
        }
    }
}
