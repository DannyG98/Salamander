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
        return sr;
    }

    public List<State> getAllStates() {
        List<State> states= sr.getAllStates();
        return states;
    }

    public String getState(String stateCanonName) {
        return null;
    }

    public void updateState() {
    }

}
