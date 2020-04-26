package com.teammander.salamander.controller;


import com.teammander.salamander.service.TransactionService;
import com.teammander.salamander.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService ts;
    @Autowired
    public TransactionController(TransactionService ts) {
        this.ts = ts;
    }

    public TransactionService getTs() {
        return this.ts;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        TransactionService ts = getTs();
        List<Transaction> foundTransactions = ts.getTransactions();
        return foundTransactions;
    }

    @PostMapping()
    public void addTransaction(@RequestBody Transaction trans) {
        TransactionService ts = getTs();
        ts.addTransaction(trans);
    }
}
