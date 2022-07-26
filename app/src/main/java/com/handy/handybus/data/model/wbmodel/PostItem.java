package com.handy.handybus.data.model.wbmodel;

public class PostItem {
    //게시글 등록 아이템
    String postTitle;
    String postContent;
    String postDate;
    String postID;
    String postUserEmail;
    String postUserName;
    int postJoinCnt;


    public PostItem(){};

    public PostItem(String postTitle, String content, String date) {
        this.postTitle = postTitle;
        this.postContent =content;
        this.postDate= date;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setPostUserEmail(String postUserEmail) {
        this.postUserEmail = postUserEmail;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public void setPostJoinCnt(int postJoinCnt) {
        this.postJoinCnt = postJoinCnt;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostID() {
        return postID;
    }

    public String getPostUserEmail() {
        return postUserEmail;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public int getPostJoinCnt() {
        return postJoinCnt;
    }
}
