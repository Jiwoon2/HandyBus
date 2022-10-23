package com.handy.handybus.ui.board;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.handy.handybus.data.model.Board;
import com.handy.handybus.data.model.Message;

public class PostViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void post(String title, String message) {
        String uid = auth.getUid();
        String name = auth.getCurrentUser().getDisplayName();

        Message msg = new Message();
        msg.setTitle(title);
        msg.setMessage(message);
        msg.setUid(uid);
        msg.setName(name);
        msg.setId(0);
        msg.setNextReReplyId(0.001);    // 해당 메시지 (리플) 에서 대댓글이 추가될 때 할당되는 값

        Board board = new Board();
        board.setTitle(title);
        board.setMessage(message);
        board.setUid(uid);
        board.setName(name);
        board.setNextReplyId(1);        // 해당 글에서 리플이 추가될 때 할당되는 값
        board.getParticipants().add(uid);

        DocumentReference reference = firestore.collection("Board").document();
        reference.set(board);
        reference.collection("Messages").document().set(msg);
    }
}
