package com.example.ns.pro_boost1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;
    Fragment6 fragment6;

    Toolbar toolbar;

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        pager = findViewById(R.id.pager);
        pager.setClipToPadding(false);  //자식들의 패딩무시(양쪽미리보기를위한선언)
        pager.setPadding(150,0,150,0); //양쪽 뷰 미리보기
        pager.setOffscreenPageLimit(3); //뒤에안보이는화면 캐싱

        final MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        fragment1 = new Fragment1();
        adapter.addItem(fragment1);
        fragment2 = new Fragment2();
        adapter.addItem(fragment2);
        fragment3 = new Fragment3();
        adapter.addItem(fragment3);
        fragment4 = new Fragment4();
        adapter.addItem(fragment4);
        fragment5 = new Fragment5();
        adapter.addItem(fragment5);
        fragment6 = new Fragment6();
        adapter.addItem(fragment6);

        pager.setAdapter(adapter);

        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("영화 목록");
        setSupportActionBar(toolbar);



        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

       // getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();
    }

    @Override  //임의의 함수만듬
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;
        if(position==0) {
            curFragment = fragment1;
            toolbar.setTitle("첫번째화면");  //타이틀수정
        }
        else if(position==1) {
            curFragment = fragment2;
            toolbar.setTitle("두번째화면");
        }
        else if(position==2) {
            curFragment = fragment3;
            toolbar.setTitle("세번째화면");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  //Navigation클릭시 자동호출
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_0) {
            Toast.makeText(this, "첫번째메뉴", Toast.LENGTH_SHORT).show();
           // onFragmentSelected(0, null);  //fragment 전환함수실행
        } else if (id == R.id.nav_1) {
            Toast.makeText(this, "두번째메뉴", Toast.LENGTH_SHORT).show();
           // onFragmentSelected(1, null);
        } else if (id == R.id.nav_2) {
            Toast.makeText(this, "세번째메뉴", Toast.LENGTH_SHORT).show();
           // onFragmentSelected(2, null);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //뷰페이져
    class MoviePagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }
        @Override
        public Fragment getItem(int position) {

            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }



       /*
        @Nullable
        @Override  //페이지마다 타이틀붙여서 리턴
       public CharSequence getPageTitle(int position) {
            return "페이지 "+position;
        }*/
    }
}
