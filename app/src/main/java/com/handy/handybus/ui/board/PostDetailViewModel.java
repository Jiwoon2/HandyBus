package com.handy.handybus.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.handy.handybus.data.model.Board;
import com.handy.handybus.data.model.Message;

import java.util.ArrayList;
import java.util.List;

public class PostDetailViewModel extends ViewModel {

    private final Board __board;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private final MutableLiveData<Board> _board;
    public final LiveData<Board> board;
    private final ListenerRegistration boardListenerRegistration;

    private final MutableLiveData<List<Message>> _messages = new MutableLiveData<>();
    public LiveData<List<Message>> message = _messages;
    private final ListenerRegistration messagesListenerRegistration;


    public PostDetailViewModel(Board board) {
        this.__board = board;

        _board = new MutableLiveData<>(board);
        this.board = _board;

        boardListenerRegistration = firestore.collection("Board").document(board.getDocumentId())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                        return;
                    }

                    if (!value.exists()) {
                        _board.setValue(null);

                    } else {
                        _board.setValue(value.toObject(Board.class));
                    }
                });

        messagesListenerRegistration = firestore.collection("Board").document(board.getDocumentId())
                .collection("Messages")
                .whereGreaterThan("id", 0)
                .orderBy("id")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                        return;
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
        boardListenerRegistration.remove();
        messagesListenerRegistration.remove();

        super.onCleared();
    }

    public String getMyUid() {
        return auth.getUid();
    }

    public void toggleParticipation() {
        Board board = this.board.getValue();
        if (board == null) return;

        String uid = getMyUid();

        if (board.getParticipants().contains(uid)) {
            firestore.collection("Board").document(board.getDocumentId()).update("participants", FieldValue.arrayRemove(uid));
        } else {
            firestore.collection("Board").document(board.getDocumentId()).update("participants", FieldValue.arrayUnion(uid));
        }
    }

    public void sendMessage(Message parentMessage, String message) {
        if (parentMessage == null) {
            DocumentReference boardReference = firestore.collection("Board").document(__board.getDocumentId());

            firestore.runTransaction(transaction -> {
                DocumentSnapshot snapshot = transaction.get(boardReference);
                Board board = snapshot.toObject(Board.class);

                if (board == null) return -1;

                double id = board.getNextReplyId();

                Message msg = new Message();
                msg.setMessage(message);
                msg.setUid(auth.getUid());
                msg.setName(auth.getCurrentUser().getDisplayName());
                msg.setId(id);
                msg.setNextReReplyId(id + 0.001);    // 해당 메시지 (리플) 에서 대댓글이 추가될 때 할당되는 값

                transaction.set(boardReference.collection("Messages").document(), msg);
                transaction.update(boardReference, "nextReplyId", FieldValue.increment(1));

                return 1;
            });
        } else {
            DocumentReference boardReference = firestore.collection("Board").document(__board.getDocumentId());
            DocumentReference parentMessageReference = boardReference.collection("Messages").document(parentMessage.getDocumentId());

            firestore.runTransaction(transaction -> {
                DocumentSnapshot snapshot = transaction.get(parentMessageReference);
                Message parentMsg = snapshot.toObject(Message.class);

                if (parentMsg == null) return -1;

                double id = parentMsg.getNextReReplyId();

                Message msg = new Message();
                msg.setMessage(message);
                msg.setUid(auth.getUid());
                msg.setName(auth.getCurrentUser().getDisplayName());
                msg.setId(id);
                msg.setNextReReplyId(id + 0.001);    // 대댓글에 다시 댓글을 달 수 없으므로, 의미 없는 값

                transaction.set(boardReference.collection("Messages").document(), msg);
                transaction.update(parentMessageReference, "nextReReplyId", FieldValue.increment(0.001));

                return 1;
            });
        }
    }
}
