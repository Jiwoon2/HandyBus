package com.handy.handybus.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.handy.handybus.data.model.BusSearchItem;
import com.handy.handybus.data.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel implements FirebaseAuth.AuthStateListener {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private final MutableLiveData<List<BusSearchItem>> _busSearchItems = new MutableLiveData<>();
    private final MutableLiveData<Profile> _profile = new MutableLiveData<>();

    public final LiveData<List<BusSearchItem>> busSearchItems = _busSearchItems;
    public final LiveData<Profile> profile = _profile;

    private final ListenerRegistration busSearchItemsListenerRegistration;

    private DatabaseReference profileReference;
    private ValueEventListener profileEventListener;


    public MainViewModel() {
        auth.addAuthStateListener(this);

        busSearchItemsListenerRegistration = firestore.collection("BusList_Search").addSnapshotListener((value, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }

            ArrayList<BusSearchItem> items = new ArrayList<>();

            for (QueryDocumentSnapshot snapshot : value) {
                String name = "";

                try {
                    long _name = snapshot.getLong("NAME");
                    name = String.valueOf(_name);

                } catch (Exception e) {
                    name = snapshot.getString("NAME");
                }

                String routeId = String.valueOf(snapshot.getLong("ROUTE_ID"));

                items.add(new BusSearchItem(snapshot.getId(), name, routeId));
            }

            _busSearchItems.setValue(items);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        auth.removeAuthStateListener(this);
        removeProfileEventListener();
        busSearchItemsListenerRegistration.remove();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            removeProfileEventListener();
            _profile.setValue(null);

        } else {
            if (profileReference == null) {
                profileReference = database.getReference().child("Profiles").child(user.getUid());
                profileEventListener = profileReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Profile profile = snapshot.getValue(Profile.class);
                        profile.setName(user.getDisplayName());
                        profile.setEmail(user.getEmail());

                        _profile.setValue(profile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    private void removeProfileEventListener() {
        if (profileReference != null && profileEventListener != null) {
            profileReference.removeEventListener(profileEventListener);
            profileReference = null;
            profileEventListener = null;
        }
    }
}
