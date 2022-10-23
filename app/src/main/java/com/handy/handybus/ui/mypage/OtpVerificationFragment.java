package com.handy.handybus.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.handy.handybus.databinding.FragmentOtpVerificationBinding;
import com.handy.handybus.ui.main.MainViewModel;

import java.util.concurrent.TimeUnit;

public class OtpVerificationFragment extends Fragment {

    private FragmentOtpVerificationBinding binding;
    private MainViewModel mainViewModel;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String verificationId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        auth.setLanguageCode("ko");

        String phoneNumber = mainViewModel.profile.getValue().getPhoneNumber();
        binding.messageTextView.setText(phoneNumber + " 로 전송된 인증 번호를 입력하신 후, 인증하기 버튼을 눌러주세요.");

        phoneNumber = phoneNumber.replace("-", "");
        phoneNumber = "+82" + phoneNumber.substring(1);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                binding.pinView.setText(phoneAuthCredential.getSmsCode());
                                verify(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(requireContext().getApplicationContext(), "오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                OtpVerificationFragment.this.verificationId = verificationId;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

        binding.confirmBtn.setOnClickListener(v -> {
            String code = binding.pinView.getText().toString().trim();
            if (code.length() != 6) return;

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            verify(credential);
        });
    }

    private void verify(PhoneAuthCredential credential) {
        if (binding.progressView.getVisibility() == View.VISIBLE) {
            return;
        }

        binding.progressView.setVisibility(View.VISIBLE);

        auth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(task -> {
            if (task.getException() != null) {
                Exception e = task.getException();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext().getApplicationContext(), "인증 코드가 맞는지 확인해 주세요.", Toast.LENGTH_SHORT).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(requireContext().getApplicationContext(), "할당량 초과", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(requireContext().getApplicationContext(), "오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }

                binding.progressView.setVisibility(View.GONE);
                return;
            }

            Toast.makeText(requireContext().getApplicationContext(), "인증되었습니다.", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        });
    }
}
