package com.teammander.salamander.controller;

import com.teammander.salamander.map.State;
import com.teammander.salamander.service.StateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    Logger logger = LoggerFactory.getLogger(TestController.class);

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
        logger.info("/getAllStates endpoint called");
        return ss.getAllStates();
        // return new ResponseEntity<>(allStates, new HttpHeaders(), HttpStatus.OK);
    }


    //get specific state data for clicking on state on map/dropdown
    @GetMapping("/getState?scn={stateName}")
    public State getState(@PathVariable String stateCanonName){
        return getSs().getState(stateCanonName);
    }

}
