package com.khackathon.handybus.activity.wbactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.adapter.wbadapter.CommentAdapter;
import com.khackathon.handybus.model.wbmodel.CommentItem;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    EditText comment_et;
    Button comment_btn;
    ArrayList<CommentItem> commentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        comment_et=findViewById(R.id.comment_et);
        comment_btn=findViewById(R.id.comment_btn);

        //서버에서 받은 게시물 데이터 할당



        //리사이클뷰로 댓글 데이터 보여주기-commentItems을 먼저 서버로 받아와야됨.
        mRecyclerView = findViewById(R.id.container_comment_list);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        commentItems= new ArrayList<>();
        mAdapter = new CommentAdapter(commentItems);
        mRecyclerView.setAdapter(mAdapter);

        //댓글 등록버튼 누르면 코맨트 아이템에 저장하고 리사이클뷰에 추가
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= comment_et.getText().toString();
                //얻을 수 있는건 내용, 날짜. 그래서 쓰는 사람의 id는 서버 연결후 같이 보내줘야됨. id랑 이름도 같이?

                commentItems.add(new CommentItem("id","date",content));
                mAdapter.notifyDataSetChanged(); //DiffUtil ?

                //등록되면 텍스트뷰 비우기
                comment_et.setText("");

            }
        });

    }
}