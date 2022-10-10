package com.khackathon.handybus.activity.wbactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    TextView postdetail_nickname;
    TextView postdetail_date;
    TextView postdetail_title;
    TextView postdetail_content;
    TextView postdetail_joinCnt;
    Button postdetail_joinBtn;
    EditText comment_et;
    Button comment_btn;

    ArrayList<String> tempItems;
    PostItem postItem;
    ArrayList<CommentItem> commentItems;
    HashMap<String, String> LoadCommentItems;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private FirebaseAuth mAuth = null;
    String userEmail;
    String userNickName;
    int joinCnt;

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
        postdetail_joinCnt= findViewById(R.id.postdetail_joinCnt);
        postdetail_joinBtn=findViewById(R.id.postdetail_joinBtn);
        comment_et = findViewById(R.id.comment_et);
        comment_btn = findViewById(R.id.comment_btn);

        //댓글 작성자 정보
        mAuth = FirebaseAuth.getInstance();
        userEmail= mAuth.getCurrentUser().getEmail();
        userNickName=mAuth.getCurrentUser().getDisplayName();


        //서버에서 받은 게시물 값 할당
        intent = getIntent();
        boardID = intent.getStringExtra("boardID");

        postItem = new PostItem();
        tempItems = new ArrayList<>();
        LoadCommentItems = new HashMap<>();

        //postItems=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Board").child(boardID); //해당하는 값의 게시글


        //게시글만 가져옴
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {

                    if(!post.getKey().equals("comment")){
                        System.out.println(post.getValue() + "anjsep");//전부 스트링 형태로 출력됨.
                        tempItems.add(post.getValue().toString());
                    }
                }

                //postItem = post.getValue(PostItem.class);
                //settext로만 쓰일거면 postItem필요없긴함.
//                postItem.setPostContent(tempItems.get(0));
//                postItem.setPostDate(tempItems.get(1));
//                postItem.setPostID(tempItems.get(2));
//                postItem.setPostTitle(tempItems.get(3));
//                postdetail_title.setText(postItem.getPostTitle());
//                postdetail_content.setText(postItem.getPostContent());
//                postdetail_date.setText(postItem.getPostDate());

                postdetail_title.setText(tempItems.get(4));
                postdetail_content.setText(tempItems.get(0));
                postdetail_date.setText(tempItems.get(1));
                postdetail_joinCnt.setText(tempItems.get(3));
                postdetail_nickname.setText(tempItems.get(6));

                joinCnt=Integer.parseInt(tempItems.get(3)); //참여자 수

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //댓글
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    System.out.println(post.getValue() + "anjsep444");//왜 이건 코맨트만이지???
                    System.out.println(post.getClass().getName() + "anjsep333");//전부 스트링 형태로 출력됨.

                    LoadCommentItems = (HashMap<String, String>) post.getValue();
                    System.out.println(LoadCommentItems + "anjsep33355");//전부 스트링 형태로 출력됨.
                    CommentItem item = new CommentItem(LoadCommentItems.get("cmtUserName"), LoadCommentItems.get("cmtUserEmail"), LoadCommentItems.get("cmtDate"), LoadCommentItems.get("cmtContent"));
                    commentItems.add(item);
                }

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //참여하기 버튼
        postdetail_joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinCnt+=1;

                Map<String, Object> jCnt = new HashMap<>();
                jCnt.put("postJoinCnt",joinCnt);
                mReference.updateChildren(jCnt);

                postdetail_joinCnt.setText(joinCnt+"");
                Toast toast=Toast.makeText(getApplicationContext(),"해당 게시글에 참여하였습니다",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //댓글란 키보드로 가리기 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //댓글 등록버튼 누르면 코맨트 아이템에 저장하고 리사이클뷰에 추가
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = comment_et.getText().toString();
                date = getDate();

                //동적배열로 댓글을 쌓음
                commentItems.add(new CommentItem(userNickName, userEmail, date, content));

                Map<String, Object> commentUpdates = new HashMap<>();
                commentUpdates.put("/comment/", commentItems);
                //commentUpdates.put("/user-posts/" + userId + "/" + key, postValues);

                mReference.updateChildren(commentUpdates);

                mAdapter.notifyDataSetChanged();

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