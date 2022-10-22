package com.handy.handybus.model.wbmodel;

public class CommentItem {
    //댓글 등록 아이템
    String cmtUserName;
    String cmtUserEmail;
    String cmtDate;
    String cmtContent;

    public CommentItem(String cmtUserName, String cmtUserEmail, String cmtDate, String cmtContent){
        this.cmtUserName=cmtUserName;
        this.cmtUserEmail=cmtUserEmail;
        this.cmtDate=cmtDate;
        this.cmtContent=cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public void setCmtDate(String cmtDate) {
        this.cmtDate = cmtDate;
    }

    public void setCmtUserName(String cmtUserName) {
        this.cmtUserName = cmtUserName;
    }

    public void setCmtUserEmail(String cmtUserEmail) {
        this.cmtUserEmail = cmtUserEmail;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public String getCmtDate() {
        return cmtDate;
    }

    public String getCmtUserName() {
        return cmtUserName;
    }

    public String getCmtUserEmail() {
        return cmtUserEmail;
    }
}
