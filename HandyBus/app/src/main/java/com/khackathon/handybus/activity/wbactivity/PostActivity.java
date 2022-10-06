package com.khackathon.handybus.activity.wbactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        overridePendingTransition(R.anim.vertical_enter, R.anim.none);

        post_btn=findViewById(R.id.post_btn);
        EditTitle=findViewById(R.id.EditTitle);
        EditContent=findViewById(R.id.EditContent);

        item=new PostItem();

        //아이템에 또 필요한거 추가
        item.setPostTitle(EditTitle.getText().toString());
        item.setPostContent(EditContent.getText().toString());

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한걸 서버에 저장한 후 액티비티 종료

                date= getDate();

                finish();
                overridePendingTransition(R.anim.none, R.anim.vertical_exit);
            }
        });





    }

    // 날짜
    private String getDate() {
        long now = System.currentTimeMillis(); //현재 시간
        Date date = new Date(now); //date 형식으로 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //포맷설정
        String getDate = dateFormat.format(date);

        return getDate;
    }
}