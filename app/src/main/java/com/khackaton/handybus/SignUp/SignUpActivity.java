package com.khackaton.handybus.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.khackaton.handybus.R;
import com.khackaton.handybus.Login.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    // 라디오버튼: 성별 선택
    public RadioGroup radioGroup;
    public RadioButton femaleButton;
    public RadioButton maleButton;
    // 체크박스: 약관
    public CheckBox checkBox1;
    public CheckBox checkBox2;
    public CheckBox checkBox3;
    //회원가입
    public Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        radioGroup = findViewById(R.id.radioGroup);
        femaleButton = findViewById(R.id.female_radioButton);
        maleButton = findViewById(R.id.male_radioButton);
        checkBox1 = findViewById(R.id.first_checkBox);
        checkBox2 = findViewById(R.id.second_checkBox);
        checkBox3 = findViewById(R.id.third_checkBox);
        signUpButton = findViewById(R.id.Submit_Signup_button);


        radioGroup.check(R.id.female_radioButton);

        femaleButton.setOnClickListener(radioButtonOnClickListener);
        maleButton.setOnClickListener(radioButtonOnClickListener);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //체크 박스 모두 선택 돼야 넘어가도록 조건 걸기
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener radioButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //데이터 베이스 저장
            if(femaleButton.isChecked()){

            }
            else{

            }
        }
    };

}