package com.teammander.salamander.transaction;

import java.util.Date;

public abstract class Transaction {

    int tid;
    String before;
    String after;
    String comment;
    String changedRegion;
    Date timeStamp;

    public Transaction(int tid, String before, String after, String comment, String changedRegion, Date timeStamp) {
        this.tid = tid;
        this.before = before;
        this.after = after;
        this.comment = comment;
        this.changedRegion = changedRegion;
        this.timeStamp = timeStamp;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getChangedRegion() {
        return changedRegion;
    }

    public void setChangedRegion(String changedRegion) {
        this.changedRegion = changedRegion;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tid=" + tid +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", comment='" + comment + '\'' +
                ", changedRegion='" + changedRegion + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
