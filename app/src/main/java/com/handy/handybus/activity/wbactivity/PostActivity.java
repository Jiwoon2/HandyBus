package com.handy.handybus.activity.wbactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.handy.handybus.R;
import com.handy.handybus.data.model.wbmodel.PostItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {
    ImageButton postBack_btn;
    Button post_btn;
    EditText EditTitle;
    EditText EditContent;
    PostItem item;

    String date;
    String boardID;

    private DatabaseReference mDatabase;
    //DatabaseReference conditionRef = mDatabase.child("Board");
    public static boolean postFlag=false;


    private FirebaseAuth mAuth = null;
    String userEmail;
    String userNickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        overridePendingTransition(R.anim.vertical_enter, R.anim.none);

        postBack_btn=findViewById(R.id.postBack_btn);
        post_btn=findViewById(R.id.post_btn);
        EditTitle=findViewById(R.id.EditTitle);
        EditContent=findViewById(R.id.EditContent);

        //게시글 작성자 정보
        mAuth = FirebaseAuth.getInstance();
        userEmail= mAuth.getCurrentUser().getEmail();
        userNickName=mAuth.getCurrentUser().getDisplayName();


        //database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한걸 서버에 저장한 후 액티비티 종료

                date= getDate();
                boardID= mDatabase.push().getKey(); //게시글 고유 값

                item=new PostItem();

                item.setPostTitle(EditTitle.getText().toString());
                item.setPostContent(EditContent.getText().toString());
                item.setPostDate(date);
                item.setPostID(boardID);
                item.setPostUserEmail(userEmail);
                item.setPostUserName(userNickName);
                item.setPostJoinCnt(0);


                //push()로 게시글id를 랜덤값으로 생성
                mDatabase.child("Board").child(boardID).setValue(item);

                finish();
                overridePendingTransition(R.anim.none, R.anim.vertical_exit);
            }
        });

        //뒤로가기 버튼
        postBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        conditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                System.out.println(text+"eeeeeeeeeeeeeee");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    // 날짜
    private String getDate() {
        long now = System.currentTimeMillis(); //현재 시간
        Date date = new Date(now); //date 형식으로 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm,ss"); //포맷설정
        String getDate = dateFormat.format(date);

        return getDate;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        conditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                textView.setText(text);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                conditionRef.setValue(editText.getText().toString());
//            }
//        });
//    }



}