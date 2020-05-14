package com.teammander.salamander.service;

import java.util.List;
import java.util.Optional;

import com.teammander.salamander.map.DataError;
import com.teammander.salamander.map.ErrorType;
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

    public DataError getError(int id) {
        ErrorRepository er = getEr();
        Optional<DataError> queryResult = er.findById(id);
        DataError foundError = queryResult.orElse(null);
        return foundError;
    }

    public List<DataError> getUnresolvedErrors() {
        ErrorRepository er = getEr();
        List<DataError> result = er.findAllByResolved(false);
        return result; 
    }

    public List<DataError> getResolvedErrors() {
        ErrorRepository er = getEr();
        List<DataError> result = er.findAllByResolved(true);
        return result;
    }

    public List<DataError> getErrorsByType(ErrorType type) {
        ErrorRepository er = getEr();
        List<DataError> result = er.findAllByEType(type);
        return result;
    }

    public void deleteError(DataError err) {
        ErrorRepository er = getEr();
        er.delete(err);
    }

    public void addError(DataError err) {
        ErrorRepository er = getEr();
        er.saveAndFlush(err);
    }

    public void markAsResolved(DataError err) {
        ErrorRepository er = getEr();
        err.setResolved(true);
        er.flush();
    }
}
