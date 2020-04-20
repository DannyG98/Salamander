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

    @GetMapping
    public List <Transaction> getTransactions(){
        return ts.getTransactions();
    }

    @PostMapping()
    public void addTransaction(@RequestBody Transaction trans){
        ts.addTransaction(trans);
    }
}
