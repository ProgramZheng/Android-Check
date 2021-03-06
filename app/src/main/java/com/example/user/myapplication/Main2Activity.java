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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public String id;
    public int intent_permissions;
    public Bundle personal_bundle,clock_on_bundle,manage_bundle;
    public TextView department,name;
    public Button go_money;
    public TableLayout data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        intent_permissions = intent.getIntExtra("permissions",0);
        /*new各分頁的bundle*/
        personal_bundle = new Bundle();
        clock_on_bundle = new Bundle();
        manage_bundle = new Bundle();
        /*personal*/
        personal_bundle.putString("id",id);
        /*clock_on*/
        clock_on_bundle.putString("id",id);
        /*manage*/
        manage_bundle.putString("id",id);
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
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mSectionsPagerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

//        mSectionsPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*menu.add 參數1:群組id, 參數2:itemId, 參數3:item順序, 參數4:item名稱*/
        if(intent_permissions>0) {
            menu.add(0, 0, 0, "註冊員工");
        }
        menu.add(0,1,1,"修改密碼");
        //將menu添加到操作欄
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 處理點擊操作欄發生的事件
        int select_id = item.getItemId();
        if (select_id == 0) {
            //初始化Intent物件，並將主畫面變成register
            Intent intent = new Intent();
            intent.setClass(Main2Activity.this, register.class);
            intent.putExtra("id",id);
            intent.putExtra("permissions",intent_permissions);
            //開啟Activity
            startActivity(intent);
        }
        if (select_id == 1) {
            //初始化Intent物件，並將主畫面變成register
            Intent intent = new Intent();
            intent.setClass(Main2Activity.this, updpassword.class);
            intent.putExtra("id",id);
            intent.putExtra("permissions",intent_permissions);
            //開啟Activity
            startActivity(intent);
        }
        if (select_id == R.id.log_out){
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
    /*FragmentStatePagerAdapter*/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    personal tab1 = new personal();
                    tab1.setArguments(personal_bundle);
                    return tab1;
                case 1:
                    clock_on tab2 = new clock_on();
                    tab2.setArguments(clock_on_bundle);
                    return tab2;
                case 2:
                    manage tab3 = new manage();
                    tab3.setArguments(manage_bundle);
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
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View view = View.inflate(Main2Activity.this, R.layout.personal, null);
//            Toast.makeText(getApplicationContext(),"qwe",Toast.LENGTH_SHORT).show();
////            data = (TableLayout) view.findViewById(R.id.data);
////            department = (TextView) view.findViewById(R.id.department);
////            name = (TextView) view.findViewById(R.id.name);
////            go_money = (Button) view.findViewById(R.id.go_money);
////            new member(getApplicationContext(),department,name,data).execute(id);
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }


    }
}
