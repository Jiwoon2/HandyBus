package com.handy.handybus.ui.auth;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.handy.handybus.ui.BaseActivity;
import com.handy.handybus.databinding.ActivitySignUpBinding;
import com.handy.handybus.ui.main.MainActivity;
import com.handy.handybus.data.model.Profile;

import java.util.Arrays;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.phoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher("KR"));
        binding.birthEditText.setTransformationMethod(null);

        binding.serviceTermLinkButton.setOnClickListener(v -> {
        });
        binding.privacyPolicyLinkButton.setOnClickListener(v -> {
        });
        binding.marketingLinkButton.setOnClickListener(v -> {
        });

        binding.signUpButton.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        if (isProgressVisibility()) return;

        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        String name = binding.nameEditText.getText().toString().trim();
        String phoneNumber = binding.phoneNumberEditText.getText().toString().trim();
        String birth = binding.birthEditText.getText().toString().trim();
        String gender = binding.femaleButton.isChecked() ? "여성" : (binding.maleButton.isChecked() ? "남성" : "");

        boolean isServiceTermChecked = binding.serviceTermCheckbox.isChecked();
        boolean isPrivacyPolicyChecked = binding.privacyPolicyCheckbox.isChecked();

        if (email.isEmpty()) {
            showToastMessage("이메일 주소를 입력해 주세요.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToastMessage("이메일 주소를 올바르게 입력해 주세요.");
            return;
        }

        if (password.isEmpty()) {
            showToastMessage("비밀번호를 입력해 주세요.");
            return;
        }

        if (!TextUtils.equals(password, confirmPassword)) {
            showToastMessage("비밀번호가 다릅니다. 다시 입력해 주세요.");
            return;
        }

        if (name.isEmpty()) {
            showToastMessage("이름을 입력해 주세요.");
            return;
        }

        if (phoneNumber.isEmpty()) {
            showToastMessage("전화번호를 입력해 주세요.");
            return;
        }

        if (phoneNumber.replace("-", "").length() != 11 || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            showToastMessage("전화번호를 올바르게 입력해 주세요.");
            return;
        }

        if (birth.isEmpty()) {
            showToastMessage("생년월일을 입력해 주세요.");
            return;
        }

        if (birth.length() != 8) {
            showToastMessage("생년월일을 올바르게 입력해 주세요.");
            return;
        }

        if (gender.isEmpty()) {
            showToastMessage("성별을 선택해 주세요.");
            return;
        }

        if (!isServiceTermChecked || !isPrivacyPolicyChecked) {
            showToastMessage("회원가입을 하시려면 이용약관과 개인정보 취급방침에 동의하셔야 합니다.");
            return;
        }

        setProgressVisibility(true);

        auth.createUserWithEmailAndPassword(email, password).continueWithTask(task -> {
            if (task.getException() != null) throw task.getException();

            FirebaseUser user = auth.getCurrentUser();
            assert user != null;

            String uid = user.getUid();

            Profile profile = new Profile(phoneNumber, birth, gender);

            return Tasks.whenAllComplete(Arrays.asList(
                    user.updateProfile(new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                    ),
                    db.getReference().child("Profiles").child(uid).setValue(profile)
            ));
        }).addOnCompleteListener(task -> {
            if (task.getException() != null) {
                Exception exception = task.getException();

                if (exception instanceof FirebaseAuthWeakPasswordException) {
                    showToastMessage("비밀번호가 취약합니다. 다시 입력해 주세요.");

                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    showToastMessage("이메일 주소가 올바르지 않습니다. 다시 입력해 주세요.");

                } else if (exception instanceof FirebaseAuthUserCollisionException) {
                    showToastMessage("이미 사용중인 이메일 주소 입니다. 다른 이메일 주소를 입력해 주세요.");

                } else {
                    Log.d("SignUpActivity", "The sign up is failed.", exception);
                    exception.printStackTrace();

                    showToastMessage("오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
                }

                setProgressVisibility(false);
                return;
            }

            startSingleTopActivity(MainActivity.class);
            finishAffinity();
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
