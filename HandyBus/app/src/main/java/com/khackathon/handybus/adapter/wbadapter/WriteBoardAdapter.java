package com.khackathon.handybus.adapter.wbadapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.activity.wbactivity.PostDetailActivity;
import com.khackathon.handybus.model.wbmodel.PostItem;

import java.util.ArrayList;

public class WriteBoardAdapter extends RecyclerView.Adapter<WriteBoardAdapter.ViewHolder>{

    private ArrayList<PostItem> items;

    public WriteBoardAdapter(ArrayList<PostItem> item){this.items=item;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.boardlist_item, parent, false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostItem item= items.get(position);
        holder.title.setText(item.getPostTitle());
        holder.content.setText(item.getPostContent());
        holder.date.setText(item.getPostDate());
        holder.joinCnt.setText(item.getPostJoinCnt()+"");
        holder.nickName.setText(item.getPostUserName());

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), PostDetailActivity.class);
                intent.putExtra("boardID",item.getPostID()); //게시글 고유 값 전달
                System.out.println(item.getPostID()+"ㄹㄷㄴ23");
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(items!=null){ //게시글이 있을때 출력
            return items.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView joinCnt;
        public TextView nickName;
        public TextView date;
        LinearLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.tv_board_title);
            content= itemView.findViewById(R.id.tv_board_content);
            joinCnt= itemView.findViewById(R.id.tv_board_joinCnt);
            nickName= itemView.findViewById(R.id.tv_board_nickName);
            date= itemView.findViewById(R.id.tv_board_date);

            item_layout= itemView.findViewById(R.id.board_item_layout);
        }
    }
}
