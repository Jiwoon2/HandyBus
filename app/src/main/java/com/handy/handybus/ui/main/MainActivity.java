package com.handy.handybus.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.handy.handybus.ui.BaseActivity;
import com.handy.handybus.R;
import com.handy.handybus.databinding.ActivityMainBinding;
import com.handy.handybus.ui.auth.LoginActivity;
import com.handy.handybus.ui.board.PostFragment;
import com.handy.handybus.ui.main.children.BusLinkFragment;
import com.handy.handybus.ui.main.children.ConfirmReservationFragment;
import com.handy.handybus.ui.main.children.ReservationFragment;
import com.handy.handybus.ui.main.children.SearchFragment;
import com.handy.handybus.ui.main.children.WriteBoardFragment;
import com.handy.handybus.ui.mypage.MyPageFragment;
import com.handy.handybus.ui.mypage.OtpVerificationFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.profile.observe(this, (profile) -> {
            if (profile != null) {
                binding.mainName.setText(profile.getName() + "님");

            } else {
                startSingleTopActivity(LoginActivity.class);
                finishAffinity();
            }
        });

        binding.viewPager.setAdapter(new ViewPagerAdapter(this));
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setUserInputEnabled(false);

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_search) {
                binding.viewPager.setCurrentItem(0);
                return true;

            } else if (itemId == R.id.navigation_reservation) {
                binding.viewPager.setCurrentItem(1);
                return true;

            } else if (itemId == R.id.navigation_confirmReservation) {
                binding.viewPager.setCurrentItem(2);
                return true;

            } else if (itemId == R.id.navigation_site) {
                binding.viewPager.setCurrentItem(3);
                return true;

            } else if (itemId == R.id.navigation_board) {
                binding.viewPager.setCurrentItem(4);
                return true;
            }

            return true;
        });

        binding.myPageButton.setOnClickListener(v -> {
            if (getSupportFragmentManager().findFragmentByTag("MyPage") == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new MyPageFragment(), "MyPage")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new SearchFragment();
                case 1:
                    return new ReservationFragment();
                case 2:
                    return new ConfirmReservationFragment();
                case 3:
                    return new BusLinkFragment();
                default:
                    return new WriteBoardFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}