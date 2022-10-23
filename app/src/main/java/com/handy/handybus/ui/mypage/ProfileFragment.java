package com.handy.handybus.ui.mypage;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.handy.handybus.data.model.Profile;
import com.handy.handybus.databinding.FragmentProfileBinding;
import com.handy.handybus.ui.main.MainViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainViewModel mainViewModel;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        for (UserInfo providerDatum : auth.getCurrentUser().getProviderData()) {
            if (TextUtils.equals(providerDatum.getProviderId(), PhoneAuthProvider.PROVIDER_ID)) {
                binding.phoneNumberEditText.setEnabled(false);
                break;
            }
        }

        mainViewModel.profile.observe(getViewLifecycleOwner(), profile -> {
            if (profile == null) return;

            binding.emailEditText.setText(profile.getEmail());
            binding.phoneNumberEditText.setText(profile.getPhoneNumber());
            binding.nameEditText.setText(profile.getName());
            binding.birthEditText.setText(profile.getBirth());

            if (TextUtils.equals(profile.getGender(), binding.femaleButton.getText().toString())) {
                binding.femaleButton.setChecked(true);

            } else {
                binding.maleButton.setChecked(true);
            }
        });

        binding.doneButton.setOnClickListener(v -> update());
    }

    private void update() {
        if (isProgressVisibility()) return;

        String email = binding.emailEditText.getText().toString().trim();
        String currentPassword = binding.currentPasswordEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        String name = binding.nameEditText.getText().toString().trim();
        String phoneNumber = binding.phoneNumberEditText.getText().toString().trim();
        String birth = binding.birthEditText.getText().toString().trim();
        String gender = binding.femaleButton.isChecked() ? "여성" : (binding.maleButton.isChecked() ? "남성" : "");

        if (currentPassword.isEmpty()) {
            showToastMessage("현재 비밀번호를 입력해 주세요.");
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

        setProgressVisibility(true);

        auth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(email, currentPassword)).continueWithTask(task -> {
                    if (task.getException() != null) throw task.getException();

                    return auth.getCurrentUser().updatePassword(password);

                }).continueWithTask(task -> {
                    if (task.getException() != null) throw task.getException();

                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;

                    return user.updateProfile(new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                    );
                }).continueWithTask(task -> {
                    if (task.getException() != null) throw task.getException();

                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;

                    String uid = user.getUid();

                    Profile profile = new Profile(phoneNumber, birth, gender);

                    return db.getReference().child("Profiles").child(uid).setValue(profile);
                })
                .addOnCompleteListener(task -> {
                    if (task.getException() != null) {
                        Exception exception = task.getException();

                        // 이미 삭제된 사용자
                        if (exception instanceof FirebaseAuthInvalidUserException) {
                            auth.signOut();
                            return;
                        }

                        if (exception instanceof FirebaseAuthWeakPasswordException) {
                            showToastMessage("비밀번호가 취약합니다. 다시 입력해 주세요.");

                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            showToastMessage("현재 비밀번호가 다릅니다. 다시 입력해 주세요.");

                        } else {
                            Log.d("ProfileFragment", "The sign up is failed.", exception);
                            exception.printStackTrace();

                            showToastMessage("오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
                        }

                        setProgressVisibility(false);
                        return;
                    }

                    auth.getCurrentUser().reload();

                    showToastMessage("회원 정보가 수정되었습니다.");
                    requireActivity().onBackPressed();
                });
    }

    private void setProgressVisibility(boolean visibility) {
        binding.touchBlocker.setVisibility(visibility ? View.VISIBLE : View.GONE);
        binding.progressIndicator.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private boolean isProgressVisibility() {
        return binding.touchBlocker.getVisibility() == View.VISIBLE;
    }

    protected void showToastMessage(String message) {
        Toast.makeText(requireActivity().getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
