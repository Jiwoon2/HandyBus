package com.khackaton.handybus.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khackaton.handybus.Direction.DirectionActivity;
import com.khackaton.handybus.Home.HomeActivity;
import com.khackaton.handybus.MyPage.MypageActivity;
import com.khackaton.handybus.R;
import com.khackaton.handybus.Reserve.ReserveActivity;

import org.jetbrains.annotations.NotNull;

public class FrameActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public Intent intent;
    public HomeActivity homeFragment;
    public ReserveActivity searchFragment;
    public DirectionActivity directionFragment;
    public MypageActivity mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_fragment);

        bottomNavigationView = findViewById(R.id.bottom_Navi);
        homeFragment = new HomeActivity();
        searchFragment = new ReserveActivity();
        directionFragment = new DirectionActivity();
        mypageFragment = new MypageActivity();

        //홈 프래그먼트를 처음으로 띄움
        setFragment(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                setFragment(item.getItemId());
                return true;
            }
        });
    }

    public void setFragment(int n){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n) { //item의 id에 따라서 fragment를 띄우는 각각의 코드를 실행시킨다.
            case R.id.navigation_home:
                fragmentTransaction.replace(R.id.frame_Fragment, homeFragment);
                fragmentTransaction.commit();

//                homeFragment.refresh_btn.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        intent=getIntent();//인텐트 받고 홈으로 넘겨.. 넘겨질련가
//                        if(intent!=null){
//
//                            homeFragment.tv_departStation.setText(intent.getStringExtra("et_departures"));
//                            homeFragment.tv_arrStation.setText(intent.getStringExtra("et_arrivals"));
//                            homeFragment.tv_personNm.setText(intent.getStringExtra("radio_type")+" "+intent.getStringExtra("et_num")+"명");
//                            homeFragment.tv_help.setText(intent.getStringExtra("radio_wheel")+", "+intent.getStringExtra("et_help"));
//                            homeFragment.tv_getonBusNm.setText(intent.getStringExtra("res_busNum"));
//
//                            if(homeFragment.tv_departStation.getText()!=""){
//                                homeFragment.tv_isReservation.setText("예약 중");
//                            }
////
//                        }
//
//
//                    }
//                });
                break;
            case R.id.navigation_search:
                fragmentTransaction.replace(R.id.frame_Fragment, searchFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_road:
                fragmentTransaction.replace(R.id.frame_Fragment, directionFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_mypage:
                fragmentTransaction.replace(R.id.frame_Fragment, mypageFragment);
                fragmentTransaction.commit();
                break;

        }
    }

}
