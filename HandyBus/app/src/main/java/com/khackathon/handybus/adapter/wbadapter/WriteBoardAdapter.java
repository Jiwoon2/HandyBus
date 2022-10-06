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

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), PostDetailActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        LinearLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.tv_board_title);
            content= itemView.findViewById(R.id.tv_board_content);
            item_layout= itemView.findViewById(R.id.board_item_layout);

        }
    }
}
