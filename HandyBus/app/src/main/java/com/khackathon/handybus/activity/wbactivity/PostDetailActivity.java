package com.khackathon.handybus.activity.wbactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khackathon.handybus.R;
import com.khackathon.handybus.adapter.wbadapter.CommentAdapter;
import com.khackathon.handybus.model.wbmodel.CommentItem;
import com.khackathon.handybus.model.wbmodel.PostItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PostDetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    TextView postdetail_nickname;
    TextView postdetail_date;
    TextView postdetail_title;
    TextView postdetail_content;
    EditText comment_et;
    Button comment_btn;

    ArrayList<String> tempItems;
    PostItem postItem;
    ArrayList<CommentItem> commentItems;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    Intent intent;
    String date;
    String boardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        postdetail_nickname = findViewById(R.id.postdetail_nickname);
        postdetail_date = findViewById(R.id.postdetail_date);
        postdetail_title = findViewById(R.id.postdetail_title);
        postdetail_content = findViewById(R.id.postdetail_content);
        comment_et = findViewById(R.id.comment_et);
        comment_btn = findViewById(R.id.comment_btn);


        //서버에서 받은 게시물 데이터 할당
        intent = getIntent();
        boardID = intent.getStringExtra("boardID");

        System.out.println(boardID + "ㄹㄷㄴ2");
        postItem= new PostItem();
        tempItems=new ArrayList<>();

        //postItems=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Board").child(boardID); //해당하는 값의 게시글

        //
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getChildren() + "anjsep"); //이상

                //아님 정말 텍스트 어래이 만들어서 반복문에 넣는ㅂ방법? 근데 여기다가 댓글도 넣을거면.. 어떻게 될ㅈ..ㅣ
                //코맨트만 if문써서 따로.... 다른곳에 저장하게..?
                for (DataSnapshot post : snapshot.getChildren()) {
                    System.out.println(post.getValue() + "anjsep");//전부 스트링 형태로 출력됨.
                    tempItems.add(post.getValue().toString());

                }

                //System.out.println(tempItems.size() + "anjsep22");//사이즈는 들어오는데..
                //postItem = post.getValue(PostItem.class);
                //값 할당- 서버에 올라간 순서대로- 아님 컨스터럭터로 한꺼번에.
                postItem.setPostContent(tempItems.get(0)); //이게 왜 널이뜰까?
                postItem.setPostDate(tempItems.get(1));
                postItem.setPostID(tempItems.get(2));
                postItem.setPostTitle(tempItems.get(3));

                postdetail_title.setText(postItem.getPostTitle());
                postdetail_content.setText(postItem.getPostContent());
                postdetail_date.setText(postItem.getPostDate());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        //댓글 등록버튼 누르면 코맨트 아이템에 저장하고 리사이클뷰에 추가
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = comment_et.getText().toString();
                //얻을 수 있는건 내용, 날짜. 그래서 쓰는 사람의 id는 서버 연결후 같이 보내줘야됨. id랑 이름도 같이?


                date= getDate();

                commentItems.add(new CommentItem("id", "date", content));
                mAdapter.notifyDataSetChanged(); //DiffUtil ?

                //등록되면 텍스트뷰 비우기
                comment_et.setText("");

            }
        });

        //리사이클뷰로 댓글 데이터 보여주기-commentItems을 먼저 서버로 받아와야됨.
        mRecyclerView = findViewById(R.id.container_comment_list);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어댑터 세팅
        commentItems = new ArrayList<>();
        mAdapter = new CommentAdapter(commentItems);
        mRecyclerView.setAdapter(mAdapter);

    }

    // 날짜
    private String getDate() {
        long now = System.currentTimeMillis(); //현재 시간
        Date date = new Date(now); //date 형식으로 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss"); //포맷설정
        String getDate = dateFormat.format(date);

        return getDate;
    }
}