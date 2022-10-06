package com.khackathon.handybus.model.wbmodel;

public class PostItem {
    //게시글 등록 아이템
    String postTitle;
    String postContent;
    String postDate;
    String postUserID;
    String postID;

    public PostItem(){};

    public PostItem(String postTitle, String content) {
        this.postTitle = postTitle;
        this.postContent =content;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }
}
