package com.khackathon.handybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.khackathon.handybus.view.home.HomeFragment;
import com.khackathon.handybus.view.notify.NotificationsFragment;
import com.khackathon.handybus.view.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    NotificationsFragment notificationsFragment;
    SearchFragment searchFragment;

    String a;

    public static Activity Main;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView= findViewById(R.id.nav_view);

        homeFragment=new HomeFragment();
        notificationsFragment= new NotificationsFragment();
        searchFragment= new SearchFragment();


        //getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,blankFragment).commit();

        //처음 화면에 고정할 화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,homeFragment).commitAllowingStateLoss();

        if(getIntent()!=null){ //인텐트 받기 //하 미친 액티비티 그대론데?
            intent = getIntent();
            a= intent.getStringExtra("asdf"); //넘겨받음
            System.out.println("2222222255"+a);
            //homeFragment.tv_isReservation.setText("예약 중"); //왜 오류가 날까
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:


//                    homeFragment.refresh_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            intent = getIntent();//인텐트 받고 홈으로 넘겨.. 넘겨질련가
//                            if (intent != null) {
//
////                                homeFragment.tv_departStation.setText(intent.getStringExtra("et_departures"));
////                                homeFragment.tv_arrStation.setText(intent.getStringExtra("et_arrivals"));
////                                homeFragment.tv_personNm.setText(intent.getStringExtra("radio_type") + " " + intent.getStringExtra("et_num") + "명");
////                                homeFragment.tv_help.setText(intent.getStringExtra("radio_wheel") + ", " + intent.getStringExtra("et_help"));
////                                homeFragment.tv_getonBusNm.setText(intent.getStringExtra("res_busNum"));
//
//                                if (homeFragment.tv_departStation.getText() != "") {
//                                    homeFragment.tv_isReservation.setText("예약 중");
//                                }
//
//                                System.out.println("22222222552"+a);
////
//                            }
//
//                        }
//                    });
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, homeFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, notificationsFragment).commit();
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, searchFragment).commit();
                    return true;
            }
            return true;
        });


        //FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.content_layout, homeFragment ).commit();

    }

    // 프래그먼트-> 프래그먼트 전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment ).commit();
    }


}