package com.khackathon.handybus.ui.writeboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.khackathon.handybus.R;
import com.khackathon.handybus.activity.wbactivity.PostActivity;
import com.khackathon.handybus.adapter.wbadapter.WriteBoardAdapter;
import com.khackathon.handybus.model.wbmodel.PostItem;

import java.util.ArrayList;

public class WriteBoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WriteBoardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<PostItem> PostingList;
    Button write_btn;

    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write_board, container, false);

        write_btn= v.findViewById(R.id.write_btn);

        PostingList= new ArrayList<>();
        //ArrayList에 값 추가하기
        PostingList.add(new PostItem("도봉순", "dkdkdk@kdkdkd.com"));
        PostingList.add(new PostItem("김월매", "dddd@gmail.com"));

        mRecyclerView = v.findViewById(R.id.container_boardList);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        mAdapter = new WriteBoardAdapter(PostingList);
        mRecyclerView.setAdapter(mAdapter);

        //누르면 게시글 입력할 수 있는 창으로 이동
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(v.getContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}