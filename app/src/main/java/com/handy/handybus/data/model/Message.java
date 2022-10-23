package com.handy.handybus.data.model;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Message {
    @DocumentId
    private String documentId;

    private double id;
    private double nextReReplyId;

    // For root
    private String title;

    private String uid;
    private String name;
    private String message;

    private Date timestamp = new Date();


    public Message() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getNextReReplyId() {
        return nextReReplyId;
    }

    public void setNextReReplyId(double nextReReplyId) {
        this.nextReReplyId = nextReReplyId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
