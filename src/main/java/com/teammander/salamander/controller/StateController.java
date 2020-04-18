package com.teammander.salamander.controller;

import com.teammander.salamander.map.State;
import com.teammander.salamander.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/state")
public class StateController {
    StateService ss;

    @Autowired
    public StateController(StateService ss) {
        this.ss = ss;
    }

    public StateService getSs() {
        return ss;
    }

    //when page loads and we get all states to display
    @GetMapping
    public ResponseEntity<List<State>> getAllStates(){
        List<State> allStates= ss.getAllStates();
        return new ResponseEntity<>(allStates, HttpStatus.OK);
    }


    //get specific state data for clicking on state on map/dropdown
    @GetMapping("{stateName}")
    public ResponseEntity getState(@PathVariable String stateName){
        return null;
    }

}
