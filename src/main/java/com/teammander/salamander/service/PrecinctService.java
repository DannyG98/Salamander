package com.teammander.salamander.service;

import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PrecinctService {
    PrecinctRepository pr;

    @Autowired
    public PrecinctService(PrecinctRepository pr) {
        this.pr = pr;
    }

    public PrecinctRepository getPr() {
        return pr;
    }

    public Precinct getPrecinct(String canonName){
        return null;
    }

    public void rmPrecinct(Precinct precinct){
    }

    public void updatePrecincts(){
    }

    public ResponseEntity addNeighbor(Precinct precinctName1, Precinct precinctName2){
        return null;
    }

    public ResponseEntity deleteNeighbor(Precinct precinctName1, Precinct precinctName2){
        return null;
    }

    public ResponseEntity find(String canonName){
        return null;
    }

    public ResponseEntity mergePrecincts(String canonName1, String canonName2){
        return null;
    }

    public ResponseEntity remove (Precinct precinct1){
        return null;
    }

}
