package com.handy.handybus.ui.main.children;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handy.handybus.data.model.UserReserv_Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfirmReservationViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    private final MutableLiveData<List<UserReserv_Item>> _reservations = new MutableLiveData<>();
    public LiveData<List<UserReserv_Item>> reservations = _reservations;
    private final DatabaseReference reference;
    private final ValueEventListener eventListener;


    public ConfirmReservationViewModel() {
        reference = db.getReference().child("Reservations").child(auth.getUid());
        eventListener = reference.orderByChild("resDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<UserReserv_Item> items = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    UserReserv_Item item = child.getValue(UserReserv_Item.class);
                    item.setKey(child.getKey());

                    items.add(item);
                }

                Collections.reverse(items);

                _reservations.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
    }

    @Override
    protected void onCleared() {
        reference.removeEventListener(eventListener);

        super.onCleared();
    }

    public void removeReservation(UserReserv_Item item) {
        reference.child(item.getKey()).removeValue();
    }
}
