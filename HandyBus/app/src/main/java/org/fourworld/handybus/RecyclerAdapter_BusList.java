package org.fourworld.handybus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapter_BusList extends RecyclerView.Adapter<RecyclerAdapter_BusList.ViewHolder>{
    ArrayList<RecyclerItem_BusList> items = new ArrayList<>();

    RecyclerAdapter_BusList(ArrayList<RecyclerItem_BusList>items){this.items = items;}

    public void clearItems(){ items.clear();   }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycleritem_buslist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RecyclerItem_BusList item =items.get(position);

        String type;
        //if문으로 구별해서 한글로 넣기
        //(1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
        if(item.busRouteType.equals(0)){

        }

        holder.routeType.setText(item.busRouteType);
        holder.busNum.setText(item.busNum);
        holder.arrmsg1.setText(item.busArrmsg1);
        holder.arrmsg2.setText(item.busArrmsg2);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView routeType;
        TextView busNum;
        TextView arrmsg1;
        TextView arrmsg2;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            routeType = itemView.findViewById(R.id.routeType);
            busNum = itemView.findViewById(R.id.busNum);
            arrmsg1= itemView.findViewById(R.id.arrmsg1);
            arrmsg2= itemView.findViewById(R.id.arrmsg2);

        }
    }
}
