package com.khackathon.handybus.activity.wbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khackathon.handybus.R;
import com.khackathon.handybus.model.wbmodel.PostItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {
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


                //conditionRef.setValue(date);

                //setvalue 해당 경로 데이터 모두 바꿈- 근데 게시글 작성이니까 ㄱㅊ
                //두번째 찰드에는 게시글구분해야되니까 게시글 고유id가 들어가겠네
                //mDatabase.child("users").child("asdf").setValue(item);

                //push()로 게시글id를 랜덤값으로 생성

                mDatabase.child("Board").child(boardID).setValue(item);

                finish();
                overridePendingTransition(R.anim.none, R.anim.vertical_exit);
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


    // 날짜
    private String getDate() {
        long now = System.currentTimeMillis(); //현재 시간
        Date date = new Date(now); //date 형식으로 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss"); //포맷설정
        String getDate = dateFormat.format(date);

        return getDate;
    }
}