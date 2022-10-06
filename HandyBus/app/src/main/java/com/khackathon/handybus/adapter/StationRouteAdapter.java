package com.khackathon.handybus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.activity.ReserveActivity;
import com.khackathon.handybus.databinding.StationrouteItemBinding;
import com.khackathon.handybus.model.StationRoute_Item;

import java.util.ArrayList;

public class StationRouteAdapter extends RecyclerView.Adapter<StationRouteAdapter.ItemViewHolder> {
    private ArrayList<StationRoute_Item> items=new ArrayList<>();
    public static String selectStationNm;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationNm = item.stationNm;

                //전역변수 플래그 값으로 구별
                //출발지 설정
                if(ReserveActivity.selectFlag==0){
                    ReserveActivity.et_departures.setText(stationNm);
                }
                //도착지 설정
                else if(ReserveActivity.selectFlag==1){
                    ReserveActivity.et_arrivals.setText(stationNm);
                }
                ReserveActivity.dialog.dismiss(); // dialog 종료
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
        if ( items != null ) {
            return items.size();
        } else {
            return 0;
        }
    }
}
