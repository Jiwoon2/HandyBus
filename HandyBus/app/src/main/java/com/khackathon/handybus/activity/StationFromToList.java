package com.khackathon.handybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.khackathon.handybus.R;

public class StationFromToList extends AppCompatActivity {

    TextView te1;
    TextView te2;
    TextView te3;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationfromto_list);

        te1=findViewById(R.id.te1);
        te2=findViewById(R.id.te2);
        te3=findViewById(R.id.te3);

        te1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                data= new Intent();
                data.putExtra("출발지","화랑대");
                setResult(0,data);
                finish();
            }
        });

        te2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                data= new Intent();
                data.putExtra("출발지","화랑대사거리");
                setResult(0,data);
                finish();
            }
        });

        te3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                data= new Intent();
                data.putExtra("출발지","서울여대");
                setResult(0,data);
                finish();
            }
        });


    }
}