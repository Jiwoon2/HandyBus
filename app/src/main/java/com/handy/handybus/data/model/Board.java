package com.handy.handybus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.Date;

public class Board implements Parcelable {

    @DocumentId
    private String documentId;

    private double nextReplyId;

    private String title;

    private String uid;
    private String name;
    private String message;

    private ArrayList<String> participants = new ArrayList<>();

    private Date timestamp = new Date();

    // 신고하기 받은 횟수
    private int numReports = 0;

    public Board() {
    }

    protected Board(Parcel in) {
        documentId = in.readString();
        nextReplyId = in.readDouble();
        title = in.readString();
        uid = in.readString();
        name = in.readString();
        message = in.readString();
        participants = in.createStringArrayList();
        numReports = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeDouble(nextReplyId);
        dest.writeString(title);
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(message);
        dest.writeStringList(participants);
        dest.writeInt(numReports);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public double getNextReplyId() {
        return nextReplyId;
    }

    public void setNextReplyId(double nextReplyId) {
        this.nextReplyId = nextReplyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumReports() {
        return numReports;
    }
    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }
}
