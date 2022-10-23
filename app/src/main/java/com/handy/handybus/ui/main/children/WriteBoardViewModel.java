package com.handy.handybus.ui.main.children;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.handy.handybus.data.model.Board;

import java.util.ArrayList;
import java.util.List;

public class WriteBoardViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Board>> _boards = new MutableLiveData<>();
    public LiveData<List<Board>> boards = _boards;

    private ListenerRegistration listenerRegistration;


    public WriteBoardViewModel() {
        listenerRegistration = firestore.collection("Board")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                    }

                    ArrayList<Board> items = new ArrayList<>();

                    for (DocumentSnapshot document : value.getDocuments()) {
                        items.add(document.toObject(Board.class));
                    }

                    _boards.setValue(items);
                });
    }

    @Override
    protected void onCleared() {
        listenerRegistration.remove();
        listenerRegistration = null;

        super.onCleared();
    }
}
