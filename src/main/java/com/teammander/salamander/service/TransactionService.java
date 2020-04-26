package com.teammander.salamander.service;

import com.teammander.salamander.repository.TransactionRepository;
import com.teammander.salamander.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    TransactionRepository tr;

    @Autowired
    public TransactionService(TransactionRepository tr) {
        this.tr = tr;
    }

    public void addTransaction(Transaction trans) {
        tr.addTransaction(trans);
    }

    public List<Transaction> getTransactions() {
        return tr.getTransactions();
    }
}
