package com.handy.handybus.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.handy.handybus.ui.BaseActivity;
import com.handy.handybus.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends BaseActivity {

    private ActivityForgotPasswordBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.sendButton.setOnClickListener(v -> send());
    }

    private void send() {
        if (isProgressVisibility()) return;

        String email = binding.emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            showToastMessage("이메일 주소를 입력해 주세요.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToastMessage("이메일 주소를 올바르게 입력해 주세요.");
            return;
        }

        setProgressVisibility(true);

        auth.setLanguageCode("ko");
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.getException() != null) {
                Exception exception = task.getException();

                if (exception instanceof FirebaseAuthInvalidUserException) {
                    showToastMessage(email + "로 가입된 사용자가 없습니다.");

                } else {
                    Log.d("ForgotPasswordActivity", "", exception);
                    exception.printStackTrace();

                    showToastMessage("오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
                }

                setProgressVisibility(false);
                return;
            }

            showToastMessage("메시지가 전송되었습니다.");
        });
    }

    private void setProgressVisibility(boolean visibility) {
        binding.touchBlocker.setVisibility(visibility ? View.VISIBLE : View.GONE);
        binding.progressIndicator.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private boolean isProgressVisibility() {
        return binding.touchBlocker.getVisibility() == View.VISIBLE;
    }
}
