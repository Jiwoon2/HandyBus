package org.fourworld.handybus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BusReservationActivity extends AppCompatActivity {

    static String eeeee;

    Intent intent;
    String busRouteNm;
    TextView res_busNum;
    //AutoCompleteTextView et_departures;
    TextView et_departures;
    //AutoCompleteTextView et_arrivals;
    TextView et_arrivals;
    EditText et_num;
    EditText et_help;

    RadioButton radio_adult;
    RadioButton radio_student;
    RadioButton radio_child;
    RadioButton radio_nonWheel;
    RadioButton radio_hasWheel;

    TextView tv_departStation;
    TextView tv_arrStation;
    TextView tv_getonBusNm;
    TextView tv_personNm;
    TextView tv_help;

    Button confirm_btn;
    String radio_type;
    String radio_wheel;

    HomeFragment HomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_reservation);

        intent= getIntent();
        busRouteNm= intent.getStringExtra("busRouteNm"); //노선명 넘겨받음

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
                finish();
                MainActivity main= (MainActivity) MainActivity.Main;
                main.finish();

                intent= new Intent(v.getContext(), MainActivity.class);
//                Bundle bundle= new Bundle();
//                System.out.println(et_departures.getText()+"8888888888854");//담아지는데

//                bundle.putString("et_departures", String.valueOf(et_departures.getText()));
//                bundle.putString("et_arrivals", String.valueOf(et_arrivals.getText()));
//                bundle.putString("radio_type",radio_type);
//                bundle.putString("et_num", String.valueOf(et_num.getText()));
//                bundle.putString("radio_wheel",radio_wheel);
//                bundle.putString("et_help", String.valueOf(et_help.getText()));
//                HomeFragment.setArguments(bundle);
               // Navigation.findNavController(v).navigate(R.id.content_layout, bundle);

                //프래그먼트에게 넘기고 메인 액티비티 시작
                //근데 난 지금 정보들을 넘거야되긴해
                //근데 이걸 표현하는 곳은... homefragment잖아...
                //그럼 이걸 번들로 보내야되나

//                bundle.putString("et_departures",et_departures.getText());

                intent.putExtra("et_departures", String.valueOf(et_departures.getText()));
                intent.putExtra("et_arrivals", String.valueOf(et_arrivals.getText()));
                intent.putExtra("radio_type",radio_type);
                intent.putExtra("res_busNum", String.valueOf(res_busNum.getText()));
                intent.putExtra("et_num", String.valueOf(et_num.getText()));
                intent.putExtra("radio_wheel",radio_wheel);
                intent.putExtra("et_help", String.valueOf(et_help.getText()));


                startActivity(intent);
            }
        });

        et_departures.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String [] list={"서울여자대학교", "화랑대 사거리", "화랑대역 4번 출구", "태릉입구역"};
//                intent=new Intent(v.getContext(), BusStopList.class);
//                startActivityForResult(intent,0);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BusReservationActivity.this);
                dlg.setTitle("출발지 선택");
                //dlg.setIcon(R.drawable.internet);
                dlg.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // array[which] = "클릭한 값"
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
//                intent=new Intent(v.getContext(), BusStopList.class);
//                startActivityForResult(intent,0);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BusReservationActivity.this);
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



//        String [] list={"서울여자대학교", "화랑대 사거리", "화랑대역 4번 출구", "태릉입구역"};
//        AutoCompleteTextView deEdit=findViewById(R.id.et_departures);
//        AutoCompleteTextView arrEdit=findViewById(R.id.et_arrivals);
//
//        deEdit.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, list));
//        arrEdit.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, list));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                et_departures.setText(data.getStringExtra("출발지"));
                break;

        }


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
                // Cheese me
            else
                    // Remove the meat
                break;
            case R.id.radio_child:
                if (checked){
                    radio_type="어린이";

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.radio_nonWheel:
                if (checked){
                    radio_wheel="휠체어 해당없음";

                }
                // Cheese me
                else
                    // I'm lactose intolerant
                    break;
            case R.id.radio_hasWheel:
                if (checked){
                    radio_wheel="휠체어 소지";

                }
                // Cheese me
                else
                    // I'm lactose intolerant
                    break;
        }
    }
}