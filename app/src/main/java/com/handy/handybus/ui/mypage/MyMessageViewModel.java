package com.handy.handybus.ui.mypage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.handy.handybus.data.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MyMessageViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Message>> _messages = new MutableLiveData<>();
    public LiveData<List<Message>> messages = _messages;

    private ListenerRegistration listenerRegistration;


    public MyMessageViewModel() {
        listenerRegistration = firestore.collectionGroup("Messages").whereEqualTo("uid", auth.getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                    }

                    ArrayList<Message> items = new ArrayList<>();

                    for (DocumentSnapshot document : value.getDocuments()) {
                        items.add(document.toObject(Message.class));
                    }

                    _messages.setValue(items);
                });
    }

    @Override
    protected void onCleared() {
        listenerRegistration.remove();
        listenerRegistration = null;

        super.onCleared();
    }
}
