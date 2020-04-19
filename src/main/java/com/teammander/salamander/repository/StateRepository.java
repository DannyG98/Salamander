package com.teammander.salamander.repository;

import com.teammander.salamander.map.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public interface StateRepository extends JpaRepository<State, String> {
    // // EntityManager em;
    // HashMap<String, State> allStates;

    // @Autowired
    // public StateRepository() throws IOException {
    //     allStates = new HashMap<String, State>();
    // }

    // public State getState(String stateCanonName) {
    //     return allStates.get(stateCanonName);
    // }

    // public void updateChanges() {
    // }

    // public List<State> getAllStates() {
    //     return new ArrayList<State>(allStates.values());
    // }

    // public void addState(State state) {
    //     allStates.put(state.getCanonName(), state);
    // }
}
