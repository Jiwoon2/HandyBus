package com.khackathon.handybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.khackathon.handybus.ui.confirmReservation.ConfirmReservationFragment;
import com.khackathon.handybus.ui.reservation.ReservationFragment;
import com.khackathon.handybus.ui.site.BusLinkFragment;
import com.khackathon.handybus.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khackathon.handybus.ui.writeboard.WriteBoardFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SearchFragment searchFragment;
    ReservationFragment reservationFragment;
    ConfirmReservationFragment confirmReservationFragment;
    BusLinkFragment busLinkFragment;
    WriteBoardFragment writeBoardFragment;

    public static Activity Main;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);

        searchFragment = new SearchFragment();
        reservationFragment= new ReservationFragment();
        confirmReservationFragment = new ConfirmReservationFragment();
        busLinkFragment = new BusLinkFragment();
        writeBoardFragment = new WriteBoardFragment();

        //처음 화면에 고정할 화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, searchFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, searchFragment).commit();
                    return true;
                case R.id.navigation_reservation:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, reservationFragment).commit();
                    return true;
                case R.id.navigation_confirmReservation:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, confirmReservationFragment).commit();
                    return true;

                case R.id.navigation_site:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, busLinkFragment).commit();
                    return true;
                case R.id.navigation_board:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, writeBoardFragment).commit();
                    return true;
            }
            return true;
        });


        //FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.content_layout, homeFragment ).commit();

    }

    // 프래그먼트-> 프래그먼트 전환
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment).commit();
    }


}