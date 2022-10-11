package com.khackathon.handybus.adapter.wbadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.model.wbmodel.CommentItem;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<CommentItem> items;

    public CommentAdapter(ArrayList<CommentItem> item){this.items=item;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentItem item= items.get(position);
        holder.userID.setText(item.getCmtUserName());
        holder.date.setText(item.getCmtDate());
        holder.content.setText(item.getCmtContent());

    }

    @Override
    public int getItemCount() {
        if(items!=null){ //댓글이 있을때 출력
            return items.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userID;
        TextView date;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            userID= itemView.findViewById(R.id.tv_cmt_userName);
            date= itemView.findViewById(R.id.tv_cmt_date);
            content= itemView.findViewById(R.id.tv_cmt_content);
        }
    }
}
