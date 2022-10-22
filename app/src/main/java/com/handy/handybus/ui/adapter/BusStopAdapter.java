package com.handy.handybus.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.R;
import com.handy.handybus.ui.search.BusListActivity;
import com.handy.handybus.data.model.BusStop_Item;
import com.handy.handybus.databinding.ItemBusstopBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ItemViewHolder> {
    private ArrayList<BusStop_Item> items = new ArrayList<>();

    public BusStopAdapter(ArrayList<BusStop_Item> items) {
        this.items = items;
    }

    public void clearItems() {
        if (items != null) {
            items.clear();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //데이터 바인딩
        ItemBusstopBinding busStopItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_busstop, parent, false);
        return new ItemViewHolder(busStopItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        BusStop_Item item = items.get(position);
        holder.busStopItemBinding.setBusstopItem(item);

        //버스 목록 페이지 연결
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BusListActivity.class);
                intent.putExtra("busStName", item.busStName); //정류소 ID 넘겨줌
                intent.putExtra("busStID", item.busStID); //정류소 ID 넘겨줌
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                v.getContext().startActivity(intent);

                //화면 전환시 텀 없애기
                ((Activity) v.getContext()).overridePendingTransition(0, 0);
            }
        });
    }


    //버스 정류장 id와 이름을 표현할 text view를 찾는다
    //화면에 표시하기 위한 메소드 정의( setItem )
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemBusstopBinding busStopItemBinding;


        public ItemViewHolder(ItemBusstopBinding busStopItemBinding) {
            super(busStopItemBinding.getRoot());
            this.busStopItemBinding = busStopItemBinding;

        }
    }

    //리사이클뷰 업데이트 -여기서 연결이 잘 안되니까 맨 마지막 정보만 가져오는거아닐까..??
    public void setItemsList(ArrayList<BusStop_Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

}
