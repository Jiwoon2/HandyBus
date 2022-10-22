package com.handy.handybus.ui.main.children;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.handy.handybus.R;

public class BusLinkFragment extends Fragment {

    View img;
    View img2;
    View img3;
    View link1;
    View link2;
    View link3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bus_link, container, false);

        img = v.findViewById(R.id.logo1);
        img2 = v.findViewById(R.id.logo2);
        img3 = v.findViewById(R.id.logo3);
        link1 = v.findViewById(R.id.link1);
        link2 = v.findViewById(R.id.link2);
        link3 = v.findViewById(R.id.link3);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yeyak.seoul.go.kr/web/search/selectPageListTotalSearch.do"));
                startActivity(intent);
            }
        });
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yeyak.seoul.go.kr/web/search/selectPageListTotalSearch.do"));
                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.seouldanurim.net/resev-info#bus"));
                startActivity(intent);
            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.seouldanurim.net/resev-info#bus"));
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abletour.kr/reserve/res02.asp?scrID=0000000125&pageNum=3&subNum=2&ssubNum=1"));
                startActivity(intent);
            }
        });
        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abletour.kr/reserve/res02.asp?scrID=0000000125&pageNum=3&subNum=2&ssubNum=1"));
                startActivity(intent);
            }
        });

        return v;
    }
}