package com.teammander.salamander.service;

import com.teammander.salamander.map.State;
import com.teammander.salamander.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return sr.findAll();
    }

    public State getState(String stateCanonName) {
        return getSr().findById(stateCanonName).orElse(null);
    }

    public void insertState(State state) {
        getSr().save(state);
        getSr().flush();
    }

    public void insertMultipleStates(List<State> states) {
        getSr().saveAll(states);
        getSr().flush();
    }
}
