package com.teammander.salamander.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teammander.salamander.map.Precinct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PrecinctRepository {
    //EntityManager em;
    HashMap<String, Precinct> allPrecincts;

    @Autowired
    public PrecinctRepository() {
        allPrecincts = new HashMap<String, Precinct>();
    }

    public Precinct findPrecinct(String canonName) {
        return allPrecincts.get(canonName);
    }

    public void insertPrecinct(Precinct precinct) {
        allPrecincts.put(precinct.getCanonName(), precinct);
    }

    public List<Precinct> getAllPrecincts() {
        return new ArrayList<Precinct>(allPrecincts.values());
    }

    public void rmPrecinct(Precinct precinct) {
        allPrecincts.remove(precinct.getCanonName());
    }
}
