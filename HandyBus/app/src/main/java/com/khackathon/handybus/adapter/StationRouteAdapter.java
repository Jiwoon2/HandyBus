package com.khackathon.handybus.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.databinding.StationrouteItemBinding;
import com.khackathon.handybus.model.StationRoute_Item;

import java.util.ArrayList;

public class StationRouteAdapter extends RecyclerView.Adapter<StationRouteAdapter.ItemViewHolder> {
    private ArrayList<StationRoute_Item> items=new ArrayList<>();

    public StationRouteAdapter(ArrayList<StationRoute_Item> items){this.items = items;}

    public void clearItems(){
        if ( items != null ) {items.clear();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final StationrouteItemBinding stationrouteItemBinding;

        public ItemViewHolder(StationrouteItemBinding stationrouteItemBinding) {
            super(stationrouteItemBinding.getRoot());
            this.stationrouteItemBinding=stationrouteItemBinding;

        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //데이터 바인딩
        StationrouteItemBinding stationrouteItemBinding=  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stationroute_item, parent, false);

        return new ItemViewHolder(stationrouteItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StationRoute_Item item= items.get(position);
        holder.stationrouteItemBinding.setStRouteitem(item);

    }

    //리사이클뷰 업데이트
    public void setItemsList(ArrayList<StationRoute_Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if ( items != null ) {
            return items.size();
        } else {
            return 0;
        }
    }
}
