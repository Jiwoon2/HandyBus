package com.khackaton.handybus.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khackaton.handybus.R;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends Fragment {
    public View view;
    public TextView tv_isReservation;
    public TextView tv_departStation;
    public TextView tv_arrStation;
    public TextView tv_getonBusNm;
    public TextView tv_personNm;
    public TextView tv_help;
    public Button refresh_btn;
    public ImageView img_bus1;
    public ImageView img_bus2;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        tv_isReservation=view.findViewById(R.id.home_tv_isReservation);
        tv_departStation=view.findViewById(R.id.tv_departStation);
        tv_arrStation=view.findViewById(R.id.home_tv_arrStation);
        tv_getonBusNm=view.findViewById(R.id.home_tv_getonBusNm);
        tv_personNm=view.findViewById(R.id.home_tv_personNm);
        tv_help=view.findViewById(R.id.home_tv_help);
        refresh_btn=view.findViewById(R.id.refresh_btn);
        img_bus1 = view.findViewById(R.id.home_img_bus1);
        img_bus2 = view.findViewById(R.id.home_img_bus2);


        return view;
    }
}
