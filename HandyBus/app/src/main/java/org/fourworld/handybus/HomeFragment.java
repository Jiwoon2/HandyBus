package org.fourworld.handybus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


public class HomeFragment extends Fragment {
    View v;
    String et_departures;
    String et_arrivals;
    String radio_type="";
    String et_num="";
    String radio_wheel="";
    String et_help="";


    TextView tv_isReservation;
    TextView tv_departStation;
    TextView tv_arrStation;
    TextView tv_getonBusNm;
    TextView tv_personNm;
    TextView tv_help;

    Button refresh_btn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home, container,false);

        tv_isReservation=v.findViewById(R.id.tv_isReservation);
        tv_departStation=v.findViewById(R.id.tv_departStation);
        tv_arrStation=v.findViewById(R.id.tv_arrStation);
        tv_getonBusNm=v.findViewById(R.id.tv_getonBusNm);
        tv_personNm=v.findViewById(R.id.tv_personNm);
        tv_help=v.findViewById(R.id.tv_help);
        refresh_btn=v.findViewById(R.id.refresh_btn);




        return  v;
    }

}