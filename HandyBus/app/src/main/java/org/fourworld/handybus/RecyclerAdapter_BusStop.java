package org.fourworld.handybus;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapter_BusStop extends RecyclerView.Adapter<RecyclerAdapter_BusStop.ViewHolder> {
    static ArrayList<RecylcerItem_BusStop> items = new ArrayList<>();
    Intent intent;

    RecyclerAdapter_BusStop(ArrayList<RecylcerItem_BusStop> items){this.items = items;}

    public void clearItems(){ items.clear();    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycleritem_busstop, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RecylcerItem_BusStop item =items.get(position);

        holder.bus_id.setText(item.busStID);
        holder.bus_nm.setText(item.busStName);

        //버스 목록 페이지 연결
        holder.busStop_container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), BusList.class);
                intent.putExtra("busStID", item.busStID); //정류소 id 넘겨줌
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //버스 정류장 id와 이름을 표현할 text view를 찾는다
    //화면에 표시하기 위한 메소드 정의( setItem )
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bus_id;
        TextView bus_nm;
        LinearLayout busStop_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_id = itemView.findViewById(R.id.busStID);
            bus_nm = itemView.findViewById(R.id.busStName);
            busStop_container= itemView.findViewById(R.id.item_busStop);
        }


    }


}
