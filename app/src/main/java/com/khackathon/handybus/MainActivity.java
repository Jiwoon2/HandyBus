package com.khackathon.handybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khackathon.handybus.activity.LoginActivity;
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

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    TextView main_name;
    ImageView main_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        main_name = findViewById(R.id.main_name);
        main_mypage= findViewById(R.id.main_mypage);

        searchFragment = new SearchFragment();
        reservationFragment= new ReservationFragment();
        confirmReservationFragment = new ConfirmReservationFragment();
        busLinkFragment = new BusLinkFragment();
        writeBoardFragment = new WriteBoardFragment();

        //이름 상단 위치
        mAuth = FirebaseAuth.getInstance();
        main_name.setText(mAuth.getCurrentUser().getDisplayName()+"님");

        //임시 로그아웃
        main_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

                signOut();
            }
        });


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

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    // 프래그먼트-> 프래그먼트 전환
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment).commit();
    }


}