package com.handy.handybus.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.handy.handybus.ui.BaseActivity;
import com.handy.handybus.ui.main.MainActivity;
import com.handy.handybus.R;
import com.handy.handybus.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 9001;

    private ActivityLoginBinding binding;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();

            overridePendingTransition(0, 0);

            return;
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.loginLogo.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        binding.loginButton.setOnClickListener(v -> signIn());

        binding.signupButton.setOnClickListener(v -> startSingleTopActivity(SignUpActivity.class));

        binding.findPasswordButton.setOnClickListener(v -> startSingleTopActivity(ForgotPasswordActivity.class));
    }

    private void signIn() {
        if (isProgressVisibility()) return;

        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

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

        setProgressVisibility(true);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.getException() != null) {
                Exception exception = task.getException();

                if (exception instanceof FirebaseAuthInvalidUserException) {
                    showToastMessage(email + "로 가입된 사용자가 없습니다.");

                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    showToastMessage("비밀번호가 틀렸습니다. 다시 확인해 주세요.");

                } else {
                    showToastMessage("오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
                }

                setProgressVisibility(false);
                return;
            }

            updateUI();
        });
    }

    private void setProgressVisibility(boolean visibility) {
        binding.progressView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private boolean isProgressVisibility() {
        return binding.progressView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                updateUI();
            }
        });
    }

    //로그인 후 이동할 화면
    private void updateUI() { //update ui code here
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}