package com.teammander.salamander.service;

import com.teammander.salamander.map.DataError;
import com.teammander.salamander.repository.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {
    ErrorRepository er;

    @Autowired
    public ErrorService(ErrorRepository er) {
        this.er = er;
    }

    public ErrorRepository getEr() {
        return this.er;
    }

    public DataError getError(int id){
        return null;
    }

    public void deleteError(DataError err){
    }
}
