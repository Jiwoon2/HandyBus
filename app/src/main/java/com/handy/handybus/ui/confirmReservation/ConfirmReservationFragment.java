package com.handy.handybus.ui.confirmReservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handy.handybus.R;
import com.handy.handybus.model.UserReserv_Item;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class ConfirmReservationFragment extends Fragment {
    View v;
    String et_departures;
    String et_arrivals;
    String radio_type="";
    String personNum="";
    String radio_wheel="";
    String et_help="";

    TextView tv_isReservation;
    TextView tv_departStation;
    TextView tv_arrStation;
    TextView tv_getonBusNm;
    TextView tv_personNm;
    TextView tv_wheel;
    TextView tv_help;
    TextView tv_reservDate;
    TextView tv_reservDepart;
    TextView tv_reservArrival;

    Button refresh_btn;


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth = null;
    String userEmail;
    HashMap<String, String> LoadReservItems;
    UserReserv_Item fff;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_confirmreservation, container,false);

        tv_isReservation=v.findViewById(R.id.tv_isReservation);
        tv_departStation=v.findViewById(R.id.tv_departStation);
        tv_arrStation=v.findViewById(R.id.tv_arrStation);
        tv_getonBusNm=v.findViewById(R.id.tv_getonBusNm);
        tv_personNm=v.findViewById(R.id.tv_personNm);
        tv_wheel=v.findViewById(R.id.tv_wheel);
        tv_help=v.findViewById(R.id.tv_help);
        refresh_btn=v.findViewById(R.id.refresh_btn);
        tv_reservDate=v.findViewById(R.id.tv_reservDate);
        tv_reservDepart=v.findViewById(R.id.tv_reservDepart);
        tv_reservArrival=v.findViewById(R.id.tv_reservArrival);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userEmail= mAuth.getCurrentUser().getEmail().split("\\.")[0];
        mReference = mDatabase.getReference("User").child(userEmail); //해당하는 값의 게시글
        LoadReservItems=new HashMap<>();



        //데이터 있으면 텍스트 변경+데이터 불러오기
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //데이터가 있을 때 불러오기
                if(snapshot.hasChildren()){
                    tv_isReservation.setText("예약 정보");

                    LoadReservItems= (HashMap<String, String>) snapshot.getValue();
                    tv_departStation.setText(LoadReservItems.get("resDepartures"));
                    tv_arrStation.setText(LoadReservItems.get("resArrivals"));
                    tv_getonBusNm.setText(LoadReservItems.get("resBusNum"));

                    personNum= LoadReservItems.get("resNum");
                    tv_personNm.setText("성인 "+personNum+"명");
                    tv_wheel.setText(LoadReservItems.get("resWheel"));
                    tv_help.setText(LoadReservItems.get("resHelp"));

                    tv_reservDate.setText(LoadReservItems.get("resDate")+" 예약");
                    tv_reservDepart.setText(LoadReservItems.get("resDepartures"));
                    tv_reservArrival.setText(LoadReservItems.get("resArrivals"));
                }
                else{
                    tv_isReservation.setText("예약 사항 없음");
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        return  v;
    }

}