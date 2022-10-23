package com.handy.handybus.ui.mypage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.handy.handybus.R;
import com.handy.handybus.databinding.FragmentMyPageBinding;
import com.handy.handybus.ui.main.MainViewModel;

public class MyPageFragment extends Fragment {

    private FragmentMyPageBinding binding;
    private MainViewModel mainViewModel;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        mainViewModel.profile.observe(getViewLifecycleOwner(), (profile) -> {
            if (profile == null) return;

            binding.nameTextView.setText(profile.getName());
            binding.emailTextView.setText(profile.getEmail());
            binding.phoneNumberTextView.setText(profile.getPhoneNumber());
        });

        binding.profileContainer.setOnClickListener(v -> {
            if (getParentFragmentManager().findFragmentByTag("Profile") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new ProfileFragment(), "Profile")
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.participationButton.setOnClickListener(v -> {
            if (getParentFragmentManager().findFragmentByTag("Participation") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new ParticipationFragment(), "Participation")
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.myMessageButton.setOnClickListener(v -> {
            if (getParentFragmentManager().findFragmentByTag("MyMessage") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new MyMessageFragment(), "MyMessage")
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.verificationButton.setOnClickListener(v -> {
            for (UserInfo providerDatum : auth.getCurrentUser().getProviderData()) {
                if (TextUtils.equals(providerDatum.getProviderId(), PhoneAuthProvider.PROVIDER_ID)) {
                    Toast.makeText(requireContext().getApplicationContext(), "인증된 계정 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (getParentFragmentManager().findFragmentByTag("Otp") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new OtpVerificationFragment(), "Otp")
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.logoutButton.setOnClickListener(v -> auth.signOut());
    }
}
