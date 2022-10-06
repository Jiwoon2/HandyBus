package com.khackathon.handybus.ui.site;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.khackathon.handybus.R;

public class BusLinkFragment extends Fragment {

    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_bus_link, container, false);

        img= v.findViewById(R.id.img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //액티-> 바로 웹브라우저로 열기 --> 브라우저를 이용해 열어서 뒤로가기해도 이전 화면으로 돌아옴
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com/"));
                startActivity(intent);
            }
        });

        return v;
    }
}