package com.example.ns.pro_boost1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ns.pro_boost1.data.MovieListArray;
import com.example.ns.pro_boost1.data.MovieListInfo;
import com.example.ns.pro_boost1.dbHelper.DatabaseMovieHelper;
import com.example.ns.pro_boost1.dbHelper.DatabaseReadHelper;
import com.google.gson.Gson;

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

    FrameLayout frame;
    int id=0;

    static int status; // 네트워크연결상태확인변수
    TextView menu;
    Animation up;
    Animation down;
    LinearLayout menulayout;
    boolean isShow =false;
    ImageView menu1;
    ImageView menu2;
    ImageView menu3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);  //네트워크권한
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {  // 권한거부 되있다면
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1); //권한허용창
        }

        status = NetworkStatus.getConnectivityStatus(getApplicationContext());  //네트워크연결상태
        DatabaseMovieHelper.openDatabase(getApplicationContext(), "boost");


        frame = findViewById(R.id.container); //화면전환을위한

        pager = findViewById(R.id.pager);
        pager.setClipToPadding(false);  //자식들의 패딩무시(양쪽미리보기를위한선언)
        pager.setPadding(150, 0, 150, 0); //양쪽 뷰 미리보기
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

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("영화 목록");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menulayout = findViewById(R.id.menuLayout);
        menu = findViewById(R.id.drawer_titlemenu);
        up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

         menu1 = findViewById(R.id.menu1);
         menu2 = findViewById(R.id.menu2);
         menu3 = findViewById(R.id.menu3);

         menulayout.bringToFront(); //앞에보이게하기
        menu1.setOnClickListener(new View.OnClickListener() {   //클릭한 메뉴로 이미지변경
            @Override
            public void onClick(View v) {
                menu.setBackgroundResource(R.drawable.order11);
                menulayout.startAnimation(up);
                isShow = !isShow;
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setBackgroundResource(R.drawable.order22);
                menulayout.startAnimation(up);
                isShow = !isShow;
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setBackgroundResource(R.drawable.order33);
                menulayout.startAnimation(up);
                isShow = !isShow;
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow) {  //보이는상태
                    menulayout.startAnimation(up);
                    isShow = !isShow;
                }else{//안보이는상태
                    menulayout.setVisibility(View.VISIBLE);
                    menulayout.startAnimation(down);
                    isShow = !isShow;
                }
            }
        });

        up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) { //올라가는애니메이션끝날때 사라지게
                menulayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

     //임의의 함수만듬  네비게이션바클릭시
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;
        if(position==0) {

        }
        else if(position==1) {

        }
        else if(position==2) {

        }
        else if(position==3) {

        }

        else if(position==4) {

        }
        else if(position==5) {

        }


      //  getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();

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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_title) {
            return true;
        }

       return false;
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  //Navigation클릭시 자동호출
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_0) {
            Toast.makeText(this, "영화 목록", Toast.LENGTH_SHORT).show();
            frame.setVisibility(View.GONE);   //상세목록제거
            pager.setVisibility(View.VISIBLE);  //뷰페이저 뜸
            setTitle("영화 목록");
            menu.setVisibility(View.VISIBLE);
           // onFragmentSelected(0, null);  //fragment 전환함수실행
        } else if (id == R.id.nav_1) {
            Toast.makeText(this, "영화 API", Toast.LENGTH_SHORT).show();
           // onFragmentSelected(1, null);
        } else if (id == R.id.nav_2) {
            Toast.makeText(this, "예매하기", Toast.LENGTH_SHORT).show();
           // onFragmentSelected(2, null);
        } else if (id == R.id.nav_share) {

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

    public void ExecuteMain(){ //프레그먼트에서 상세보기클릭시  실행하는함수


        getSupportActionBar().setTitle("영화 상세");
        menu.setVisibility(View.INVISIBLE);  //정렬안보이게
        Fragment exfragment =  new MainActivity();

        DatabaseReadHelper.openDatabase(getApplicationContext(), "boost");

        getSupportFragmentManager().beginTransaction().replace(R.id.container, exfragment).commit();

        pager.setVisibility(View.GONE);  //뷰페이지사라짐
        frame.setVisibility(View.VISIBLE);  //상세목록 뜸
    }

    

}
