package org.fourworld.handybus;

import android.os.Bundle;

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

        //처음 화면에 고정할 화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,homeFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
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