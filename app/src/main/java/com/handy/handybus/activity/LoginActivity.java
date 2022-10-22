package com.handy.handybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.handy.handybus.MainActivity;
import com.handy.handybus.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private Button login_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_login_button = findViewById(R.id.login_login_button); //임의로 구글 로그인 사용
        mAuth = FirebaseAuth.getInstance();

        //자동 로그인
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//        if (login_login_button.getChildCount() > 0) {
//            View signInButtonInnerContent = login_login_button.getChildAt(0);
//            signInButtonInnerContent.setContentDescription("구글 로그인");
//        }


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
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    //로그인 후 이동할 화면
    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}