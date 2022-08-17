package com.khackaton.handybus.Reserve;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khackaton.handybus.Home.HomeActivity;
import com.khackaton.handybus.R;

import org.jetbrains.annotations.NotNull;

public class ReserveActivity extends Fragment {
    public View view;
    public Intent intent;
    public String busRouteNm;
    public TextView res_busNum;
    public EditText et_departures;
    public EditText et_arrivals;
    public EditText et_num;
    public EditText et_help;

    public RadioButton radio_adult;
    public RadioButton radio_student;
    public RadioButton radio_child;
    public RadioButton radio_nonWheel;
    public RadioButton radio_hasWheel;

    public TextView tv_departStation;
    public TextView tv_arrStation;
    public TextView tv_getonBusNm;
    public TextView tv_personNm;
    public TextView tv_help;

    public Button confirm_btn;
    public String radio_type;
    public String radio_wheel;

    public Bundle bundle;
    HomeActivity homeActivity;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reserve_fragment, container, false);

//        /*이러면 안 되는데,, MVC 패턴 만든다!*/
//        res_busNum= getView().findViewById(R.id.res_busNum);
//        et_departures= getView().findViewById(R.id.et_departures);
//        et_arrivals= getView().findViewById(R.id.et_arrivals);
//        et_num= getView().findViewById(R.id.et_num);
//        et_help= getView().findViewById(R.id.et_help);
//        radio_adult= getView().findViewById(R.id.radio_adult);
//        radio_student= getView().findViewById(R.id.radio_student);
//        radio_child= getView().findViewById(R.id.radio_child);
//        radio_nonWheel= getView().findViewById(R.id.radio_nonWheel);
//        radio_hasWheel= getView().findViewById(R.id.radio_hasWheel);
//        confirm_btn= getView().findViewById(R.id.confirm_btn);
//
//        bundle = new Bundle();
//        busRouteNm=bundle.getString("busRouteNm");
//
//        res_busNum.setText(busRouteNm);
//        homeActivity= new HomeActivity();
//
//        /*예약하기 버튼 누르면 데이터베이스로 가도록 하자*/
//        confirm_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
////                finish();
////                MainActivity main= (MainActivity) MainActivity.Main;
////                main.finish();
////
////                intent= new Intent(v.getContext(), MainActivity.class);
//////                Bundle bundle= new Bundle();
//////                System.out.println(et_departures.getText()+"8888888888854");//담아지는데
////
//////                bundle.putString("et_departures", String.valueOf(et_departures.getText()));
//////                bundle.putString("et_arrivals", String.valueOf(et_arrivals.getText()));
//////                bundle.putString("radio_type",radio_type);
//////                bundle.putString("et_num", String.valueOf(et_num.getText()));
//////                bundle.putString("radio_wheel",radio_wheel);
//////                bundle.putString("et_help", String.valueOf(et_help.getText()));
//////                HomeFragment.setArguments(bundle);
////                // Navigation.findNavController(v).navigate(R.id.content_layout, bundle);
////
////                //프래그먼트에게 넘기고 메인 액티비티 시작
////                //근데 난 지금 정보들을 넘거야되긴해
////                //근데 이걸 표현하는 곳은... homefragment잖아...
////                //그럼 이걸 번들로 보내야되나
////
//////                bundle.putString("et_departures",et_departures.getText());
////
////                intent.putExtra("et_departures", String.valueOf(et_departures.getText()));
////                intent.putExtra("et_arrivals", String.valueOf(et_arrivals.getText()));
////                intent.putExtra("radio_type",radio_type);
////                intent.putExtra("res_busNum", String.valueOf(res_busNum.getText()));
////                intent.putExtra("et_num", String.valueOf(et_num.getText()));
////                intent.putExtra("radio_wheel",radio_wheel);
////                intent.putExtra("et_help", String.valueOf(et_help.getText()));
////
////
////                startActivity(intent);
//            }
//        });

        return view;
    }
//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.radio_adult:
//                if (checked){
//                    radio_type="일반";
//                    //tv_personNm.setText("일반 ");
//                    //tv_personNm.append(et_num+"명");
//                }
//                else
//                    // Remove the meat
//                    break;
//            case R.id.radio_student:
//                if (checked){
//
//                    radio_type="학생";
//                }
//                // Cheese me
//                else
//                    // Remove the meat
//                    break;
//            case R.id.radio_child:
//                if (checked){
//                    radio_type="어린이";
//
//                }
//                // Cheese me
//                else
//                    // I'm lactose intolerant
//                    break;
//            case R.id.radio_nonWheel:
//                if (checked){
//                    radio_wheel="휠체어 해당없음";
//
//                }
//                // Cheese me
//                else
//                    // I'm lactose intolerant
//                    break;
//            case R.id.radio_hasWheel:
//                if (checked){
//                    radio_wheel="휠체어 소지";
//
//                }
//                // Cheese me
//                else
//                    // I'm lactose intolerant
//                    break;
//        }
//    }
}
