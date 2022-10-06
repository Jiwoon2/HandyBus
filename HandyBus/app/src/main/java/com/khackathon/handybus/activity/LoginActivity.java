package com.khackathon.handybus.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.khackathon.handybus.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
//
//        binding = FragmentLoginBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.fragment_login);
//
//        loginViewModel.getUser().observe(this, new Observer<Login_Item>() {
//            @Override
//            public void onChanged(@Nullable Login_Item login_Item) {
//
//                if (TextUtils.isEmpty(Objects.requireNonNull(login_Item).getStrEmailAddress())) {
//                    binding.loginID.setError("Enter an E-Mail Address");
//                    binding.loginID.requestFocus();
//                }
//                else if (!login_Item.isEmailValid()) {
//                    binding.loginID.setError("Enter a Valid E-mail Address");
//                    binding.loginID.requestFocus();
//                }
//                else if (TextUtils.isEmpty(Objects.requireNonNull(login_Item).getStrPassword())) {
//                    binding.loginPW.setError("Enter a Password");
//                    binding.loginPW.requestFocus();
//                }
//                else if (!login_Item.isPasswordLengthGreaterThan5()) {
//                    binding.loginPW.setError("Enter at least 6 Digit password");
//                    binding.loginPW.requestFocus();
//                }
//                else {
//                    binding.loginID.setText(login_Item.getStrEmailAddress());
//                    binding.loginPW.setText(login_Item.getStrPassword());
//                }
//
//            }
//        });

    }
}