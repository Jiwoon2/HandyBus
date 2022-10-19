package com.handy.handybus.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.R;
import com.handy.handybus.databinding.ItemStationrouteBinding;
import com.handy.handybus.data.model.StationRoute_Item;

import java.util.ArrayList;

public class StationRouteAdapter extends RecyclerView.Adapter<StationRouteAdapter.ItemViewHolder> {
    private ArrayList<StationRoute_Item> items = new ArrayList<>();
    private Consumer<StationRoute_Item> onItemClickListener;

    public StationRouteAdapter(ArrayList<StationRoute_Item> items) {
        this.items = items;
    }

    public void clearItems() {
        if (items != null) {
            items.clear();
        }
    }

    public void setOnItemClickListener(Consumer<StationRoute_Item> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //데이터 바인딩
        ItemStationrouteBinding stationrouteItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_stationroute, parent, false);

        return new ItemViewHolder(stationrouteItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StationRoute_Item item = items.get(position);
        holder.stationrouteItemBinding.setStRouteitem(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.accept(item);
                }
            }
        });
    }

    //리사이클뷰 업데이트
    public void setItemsList(ArrayList<StationRoute_Item> items) {
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

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemStationrouteBinding stationrouteItemBinding;

        public ItemViewHolder(ItemStationrouteBinding stationrouteItemBinding) {
            super(stationrouteItemBinding.getRoot());
            this.stationrouteItemBinding = stationrouteItemBinding;

        }
    }
}
