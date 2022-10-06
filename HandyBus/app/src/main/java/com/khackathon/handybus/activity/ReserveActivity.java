package com.khackathon.handybus.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.khackathon.handybus.ui.home.HomeFragment;
import com.khackathon.handybus.MainActivity;
import com.khackathon.handybus.R;

public class ReserveActivity extends AppCompatActivity {

    Intent intent;
    String busRouteNm;
    TextView res_busNum;
    TextView et_departures;
    TextView et_arrivals;
    EditText et_num;
    EditText et_help;

    RadioButton radio_adult;
    RadioButton radio_student;
    RadioButton radio_child;
    RadioButton radio_nonWheel;
    RadioButton radio_hasWheel;

    Button confirm_btn;
    String radio_type;
    String radio_wheel;

    com.khackathon.handybus.ui.home.HomeFragment HomeFragment;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_reservation);

        intent= getIntent();
        busRouteNm= intent.getStringExtra("busRouteNm"); //노선명 넘겨받음
        System.out.println("2255"+busRouteNm);

        res_busNum=findViewById(R.id.res_busNum);
        et_departures=findViewById(R.id.et_departures);
        et_arrivals=findViewById(R.id.et_arrivals);
        et_num=findViewById(R.id.et_num);
        et_help=findViewById(R.id.et_help);
        radio_adult=findViewById(R.id.radio_adult);
        radio_student=findViewById(R.id.radio_student);
        radio_child=findViewById(R.id.radio_child);
        radio_nonWheel=findViewById(R.id.radio_nonWheel);
        radio_hasWheel=findViewById(R.id.radio_hasWheel);
        confirm_btn=findViewById(R.id.confirm_btn);

        res_busNum.setText(busRouteNm);
        HomeFragment= new HomeFragment();

        confirm_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                intent= new Intent(v.getContext(), MainActivity.class);

                intent.putExtra("asdf", "ㄹㄷㄴㄷ");
//                intent.putExtra("et_departures", String.valueOf(et_departures.getText()));
//                intent.putExtra("et_arrivals", String.valueOf(et_arrivals.getText()));
//                intent.putExtra("radio_type",radio_type);
//                intent.putExtra("res_busNum", String.valueOf(res_busNum.getText()));
//                intent.putExtra("et_num", String.valueOf(et_num.getText()));
//                intent.putExtra("radio_wheel",radio_wheel);
//                intent.putExtra("et_help", String.valueOf(et_help.getText()));

                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); //이전 액티비티 모두 종료
                //근데...... 검색탭임.................. 그래서 인텐트 해도 넘겨준값 못받음...

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //이전 액티비티 모두 종료
                startActivity(intent);

                //화면 전환시 텀 없애기
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ((Activity)v.getContext()).overridePendingTransition(0,0);
            }
        });

        et_departures.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String [] list={"서울여자대학교", "화랑대 사거리", "화랑대역 4번 출구", "태릉입구역"};

                AlertDialog.Builder dlg = new AlertDialog.Builder(ReserveActivity.this);
                dlg.setTitle("출발지 선택");
                dlg.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_departures.setText(list[which]);
                        //Toast.makeText(getApplicationContext(), list[which],Toast.LENGTH_LONG).show();
                    }
                });
                dlg.show();
            }
        });

        et_arrivals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String [] list={"서울여자대학교", "화랑대 사거리", "화랑대역 4번 출구", "태릉입구역"};

                AlertDialog.Builder dlg = new AlertDialog.Builder(ReserveActivity.this);
                dlg.setTitle("도착지 선택");
                //dlg.setIcon(R.drawable.internet);
                dlg.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // array[which] = "클릭한 값"
                        et_arrivals.setText(list[which]);
                        //Toast.makeText(getApplicationContext(), list[which],Toast.LENGTH_LONG).show();
                    }
                });
                dlg.show();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_adult:
                if (checked){
                    radio_type="일반";
                    //tv_personNm.setText("일반 ");
                    //tv_personNm.append(et_num+"명");
                }
            else
                // Remove the meat
                break;
            case R.id.radio_student:
                if (checked){

                    radio_type="학생";
                }
            else
                break;
            case R.id.radio_child:
                if (checked){
                    radio_type="어린이";

                }
            else
                break;
            case R.id.radio_nonWheel:
                if (checked){
                    radio_wheel="휠체어 해당없음";

                }
                else
                    break;
            case R.id.radio_hasWheel:
                if (checked){
                    radio_wheel="휠체어 소지";

                }
                else
                    break;
        }
    }

    //이전화면으로 이동시 텀 없애기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}