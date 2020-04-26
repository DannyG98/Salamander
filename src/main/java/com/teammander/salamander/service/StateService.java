package com.teammander.salamander.service;

import com.teammander.salamander.map.State;
import com.teammander.salamander.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {
    StateRepository sr;

    @Autowired
    public StateService(StateRepository sr) {
        this.sr = sr;
    }

    public StateRepository getSr() {
        return this.sr;
    }

    public List<State> getAllStates() {
        List<State> allStates = sr.findAll();
        return allStates;
    }

    public State getState(String stateCanonName) {
        StateRepository sr = getSr();
        Optional<State> queryResult = sr.findById(stateCanonName);
        State foundState = queryResult.orElse(null);
        return foundState;
    }

    public void insertState(State state) {
        StateRepository sr = getSr();
        sr.saveAndFlush(state);
    }

    public void insertMultipleStates(List<State> states) {
        StateRepository sr = getSr();
        sr.saveAll(states);
        sr.flush();
    }
}
