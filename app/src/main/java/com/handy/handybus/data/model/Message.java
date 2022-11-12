package com.handy.handybus.data.model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
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

    // 신고하기 받은 횟수
    private int numReports = 0;

    //신고한 사람의 목록
    private ArrayList<String> whoReport = new ArrayList<>();

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

    public int getNumReports() {
        return numReports;
    }
    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }

    public ArrayList<String> getWhoReport() {
        return whoReport;
    }

    public void setWhoReport(ArrayList<String> whoReport) {
        this.whoReport = whoReport;
    }

}
