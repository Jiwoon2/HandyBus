package com.khackathon.handybus.ui.writeboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khackathon.handybus.R;
import com.khackathon.handybus.activity.wbactivity.PostActivity;
import com.khackathon.handybus.adapter.wbadapter.WriteBoardAdapter;
import com.khackathon.handybus.model.wbmodel.PostItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WriteBoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WriteBoardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<PostItem> PostingList;
    Button write_btn;

    Intent intent;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;


    Button post_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write_board, container, false);

        write_btn= v.findViewById(R.id.write_btn);

        //서버에서 데이터 가져오기 -> 리사이클뷰 코드 전에 있어야 됨!!!
        PostingList= new ArrayList<>();
        //ArrayList에 값 추가하기
//        PostingList.add(new PostItem("도봉순", "dkdkdk@kdkdkd.com","ee"));
//        PostingList.add(new PostItem("김월매", "dddd@gmail.com","eew"));

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("Board"); // 변경값을 확인할 child 이름

        //false니까 지금 출력되는거고... 그래서 2개가 뜨는거임 추가된건 추가된것만 뜨고있음지금..

//        PostActivity.postFlag=false;
//
//        if(!PostActivity.postFlag){
//
//        }

//            //한 번만 가져옴
//        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot post : snapshot.getChildren()) {
//                    PostItem item= post.getValue(PostItem.class);
//                    PostingList.add(item);
//
//                    //PostingList.add(post.getValue(PostItem.class)); //바로 보여지지않음..
//                    System.out.println(PostingList.size()+"ㄹㄷㄴ");
//
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        //바뀐 것만 가져옴
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                PostItem item= snapshot.getValue(PostItem.class);
                PostingList.add(item);

                mAdapter.notifyDataSetChanged();

                System.out.println("jjjjjjjjj");
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


        mRecyclerView = v.findViewById(R.id.container_boardList);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어댑터 세팅
        mAdapter = new WriteBoardAdapter(PostingList);
        mRecyclerView.setAdapter(mAdapter);



        //initDatabase();



        //전부 다시
//        mReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //리스트를 클리어안하면 중복됨..
//                PostingList.clear();
//
//                for (DataSnapshot post : dataSnapshot.getChildren()) {
//                    PostingList.add(post.getValue(PostItem.class)); //바로 보여지지않음..
//                    System.out.println(PostingList.size()+"ㄹㄷㄴ");
//
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });




        //누르면 게시글 입력할 수 있는 창으로 이동
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(v.getContext(), PostActivity.class);
                startActivity(intent);
                //getActivity().startActivityForResult(intent,5);

            }
        });

        return v;
    }


    //    private void initDatabase() {
//
//        mDatabase = FirebaseDatabase.getInstance();
//
//        mReference = mDatabase.getReference("Board");
//        //mReference.child("log").setValue("check");
//
//        mChild = new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mReference.addChildEventListener(mChild);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mReference.removeEventListener(mChild);
//    }

}