package com.teammander.salamander.transaction;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public abstract class Transaction {

    int tid;
    String before;
    String after;
    String who;
    String what;
    List<Comment> comments;
    Date timeCreated;

    @Id
    @GeneratedValue
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Lob
    @Column(name = "before")
    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    @Lob
    @Column(name = "after")
    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @OneToMany(mappedBy = "owner_transaction", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comment) {
        this.comments = comment;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_created")
    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
