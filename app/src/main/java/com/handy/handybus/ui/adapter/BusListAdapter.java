package com.handy.handybus.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.R;
import com.handy.handybus.databinding.ItemBuslistBinding;
import com.handy.handybus.ui.search.BusInfoActivity;
import com.handy.handybus.data.model.BusList_Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.ViewHolder> {
    private final String busStName;
    ArrayList<BusList_Item> items = new ArrayList<>();
    //RecyclerItem_BusList item;

    public BusListAdapter(String busStName, ArrayList<BusList_Item> items) {
        this.busStName = busStName;
        this.items = items;
    }

    public void clearItems() {
        if (items != null) {
            items.clear();
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.recycleritem_buslist, parent, false);
//        return new ViewHolder(itemView);

        //데이터 바인딩
        ItemBuslistBinding buslistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_buslist, parent, false);
        return new ViewHolder(buslistItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusList_Item item = items.get(position);

        String type = "";
        //(1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
        if (item.busRouteType.equals("1")) {
            type = "공항";
        } else if (item.busRouteType.equals("2")) {
            type = "마을";
        } else if (item.busRouteType.equals("3")) {
            type = "간선";
        } else if (item.busRouteType.equals("4")) {
            type = "지선";
        } else if (item.busRouteType.equals("5")) {
            type = "순환";
        } else if (item.busRouteType.equals("6")) {
            type = "광역";
        } else if (item.busRouteType.equals("7")) {
            type = "인천";
        } else if (item.busRouteType.equals("8")) {
            type = "경기";
        } else if (item.busRouteType.equals("9")) {
            type = "폐지";
        } else {
            type = "공용";
        }

        holder.buslistBinding.setBuslistItem(item); //설정한다음에 type할당해줘야됨
        holder.routeType.setText(type); //타입 표기하기 위해 바인딩 적용 x

        //버스 운영정보 페이지 연결
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BusInfoActivity.class);
                intent.putExtra("busStName", busStName);
                intent.putExtra("busNumber", item.busRtNm);
                intent.putExtra("busRouteId", item.busRouteId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                v.getContext().startActivity(intent);

                //화면 전환시 텀 없애기
                ((Activity) v.getContext()).overridePendingTransition(0, 0);
            }
        });

//        holder.routeType.setText(type);
//        holder.busNum.setText(item.busNum);
//        holder.arrmsg1.setText(item.busArrmsg1);
//        holder.arrmsg2.setText(item.busArrmsg2);

//        holder.busList_container.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(v.getContext(), BusInfo.class);
//                intent.putExtra("busRouteId",item.busRtNm);
//                v.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView routeType;
//        TextView busNum;
//        TextView arrmsg1;
//        TextView arrmsg2;

        private final ItemBuslistBinding buslistBinding;

        public ViewHolder(@NotNull ItemBuslistBinding buslistBinding) {
            super(buslistBinding.getRoot());
            this.buslistBinding = buslistBinding;

            routeType = itemView.findViewById(R.id.routeType);
//            busNum = itemView.findViewById(R.id.busNum);
//            arrmsg1= itemView.findViewById(R.id.arrmsg1);
//            arrmsg2= itemView.findViewById(R.id.arrmsg2);

            //System.out.println("jhhhhhoij"+buslistBinding.getBuslistItem().getBusAdirection());
        }
    }

    //리사이클뷰 업데이트
    public void setItemsList(ArrayList<BusList_Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
