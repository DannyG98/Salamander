package com.teammander.salamander.service;

import com.teammander.salamander.map.State;
import com.teammander.salamander.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
        return sr;
    }

    public List<State> getAllStates() {
        List<State> states= sr.findAll();
        return states;
    }

    public State getState(String stateCanonName) {
        return getSr().findById(stateCanonName)
                .orElseThrow(() -> new ResourceNotFoundException("State not found!"));
    }

    public void updateState() {
    }

    public void addState(State state) {
        getSr().save(state);
    }
}
