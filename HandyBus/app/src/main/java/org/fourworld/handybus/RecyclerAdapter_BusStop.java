package org.fourworld.handybus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapter_BusStop extends RecyclerView.Adapter<RecyclerAdapter_BusStop.ViewHolder> {
    static ArrayList<RecylcerItem_BusStop> items = new ArrayList<>();

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

        //상세페이지연결

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_id = itemView.findViewById(R.id.busStID);
            bus_nm = itemView.findViewById(R.id.busStName);
        }


    }


}
