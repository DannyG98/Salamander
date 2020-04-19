package com.teammander.salamander.repository;

import java.util.HashMap;

import com.teammander.salamander.map.Precinct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecinctRepository extends JpaRepository<Precinct, String> {
    // //EntityManager em;
    // HashMap<String, Precinct> allPrecincts;

    // @Autowired
    // public PrecinctRepository() {
    //     allPrecincts = new HashMap<>();
    // }

    // public Precinct findPrecinct(String canonName) {
    //     return allPrecincts.get(canonName);
    // }

    // public void rmPrecinct(Precinct precinct) {
    //     allPrecincts.remove(precinct.getCanonName());
    // }

    // public void updateChanges() {
    // }
}
