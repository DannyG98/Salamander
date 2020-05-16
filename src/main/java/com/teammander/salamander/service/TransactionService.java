package com.teammander.salamander.service;

import com.teammander.salamander.repository.CommentRepository;
import com.teammander.salamander.repository.TransactionRepository;
import com.teammander.salamander.transaction.Comment;
import com.teammander.salamander.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    TransactionRepository tr;
    CommentRepository cr;

    @Autowired
    public TransactionService(TransactionRepository tr, CommentRepository cr) {
        this.tr = tr;
    }

    public TransactionRepository getTr() {
        return this.tr;
    }

    public CommentRepository getCr() {
        return this.cr;
    }

    public void addTransaction(Transaction trans) {
        tr.saveAndFlush(trans);
    }

    public List<Transaction> getAllTransactions() {
        return tr.findAll();
    }

    public void addComment(int tid, String comment) {
        TransactionRepository tr = getTr();
        Transaction targetTrans = tr.findById(tid).orElseThrow();
        Comment newComment = new Comment();
        newComment.setOwnerTransaction(targetTrans);
        newComment.setComment(comment);
        targetTrans.getComments().add(newComment);
        tr.flush();
    }

    public void updateComment(int cid, String update) {
        CommentRepository cr = getCr();
        Comment targetComment = cr.findById(cid).orElseThrow();
        targetComment.setComment(update);
        cr.flush();
    }
}
