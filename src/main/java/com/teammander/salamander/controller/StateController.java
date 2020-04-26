package com.teammander.salamander.controller;

import com.teammander.salamander.map.State;
import com.teammander.salamander.service.StateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;


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
    @GetMapping("/getAllStates")
    public List<State> getAllStates(){
        return ss.getAllStates();
    }

    //get specific state data for clicking on state on map/dropdown
    @GetMapping("/getState/{stateCanonName}")
    public State getState(@PathVariable String stateCanonName){
        return getSs().getState(stateCanonName);
    }

    @PostMapping("/uploadState")
    public void uploadState(@RequestBody State state) {
        getSs().insertState(state);
    }

    @PostMapping("/multiUploadStates")
    public void multiUploadState(@RequestBody List<State> states) {
        getSs().insertMultipleStates(states);
    }
    
}
