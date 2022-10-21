package com.handy.handybus.ui.main.children;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.handy.handybus.data.model.StationRoute_Item;
import com.handy.handybus.data.model.UserReserv_Item;
import com.handy.handybus.data.source.StationRouteAPI;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public class ReservationViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    private final MutableLiveData<String> _selectedBusRouteId = new MutableLiveData<>();

    public LiveData<String> selectedBusRouteId = _selectedBusRouteId;
    public LiveData<List<StationRoute_Item>> routeItems;

    public ReservationViewModel(Context context) {
        routeItems = Transformations.switchMap(_selectedBusRouteId, input -> {
            if (!TextUtils.isEmpty(input)) {
                try {
                    return new StationRouteAPI().searchStationRouteV2(context, input);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            return new MutableLiveData<>(Collections.emptyList());
        });
    }

    public void setBusRouteId(String routeId) {
        _selectedBusRouteId.setValue(routeId);
    }

    public void reservation(UserReserv_Item item) {
        db.getReference().child("Reservations").child(auth.getUid()).push().setValue(item);
    }

    public void update(UserReserv_Item item) {
        db.getReference().child("Reservations").child(auth.getUid()).child(item.getKey()).setValue(item);
    }
}
