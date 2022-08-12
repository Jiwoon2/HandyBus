package org.fourworld.handybus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.fourworld.handybus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    NotificationsFragment notificationsFragment;
    SearchFragment searchFragment;
    BookmarkFragment bookmarkFragment;
    MypageFragment mypageFragment;

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
        bookmarkFragment=new BookmarkFragment();
        mypageFragment= new MypageFragment();

        Main=MainActivity.this;

        //처음 화면에 고정할 화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,homeFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:

                    homeFragment.refresh_btn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            intent=getIntent();//인텐트 받고 홈으로 넘겨.. 넘겨질련가
                            if(intent!=null){

                                homeFragment.tv_departStation.setText(intent.getStringExtra("et_departures"));
                                homeFragment.tv_arrStation.setText(intent.getStringExtra("et_arrivals"));
                                homeFragment.tv_personNm.setText(intent.getStringExtra("radio_type")+" "+intent.getStringExtra("et_num")+"명");
                                homeFragment.tv_help.setText(intent.getStringExtra("radio_wheel")+", "+intent.getStringExtra("et_help"));
                                homeFragment.tv_getonBusNm.setText(intent.getStringExtra("res_busNum"));

                                if(homeFragment.tv_departStation.getText()!=""){
                                    homeFragment.tv_isReservation.setText("예약 중");
                                }
//
                            }


                        }
                    });


                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, homeFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, notificationsFragment).commit();
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, searchFragment).commit();
                    return true;
                case R.id.navigation_bookmark:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, bookmarkFragment).commit();
                    return true;
                case R.id.navigation_mypage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, mypageFragment).commit();
                    return true;
            }
            return true;
        });



    }

}