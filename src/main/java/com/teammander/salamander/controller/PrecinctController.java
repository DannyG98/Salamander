package com.teammander.salamander.controller;

import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/precinct")
public class PrecinctController {
    PrecinctService ps;

    @Autowired
    public PrecinctController(PrecinctService ps) {
        this.ps = ps;
    }

    public PrecinctService getPs() {
        return ps;
    }

    @GetMapping
    public ResponseEntity find(String canonName) {
        return null;
    }

    public ResponseEntity addNeighbor(Precinct precinctName1, Precinct precinctName2) {
        return null;
    }

    public ResponseEntity deleteNeighbor(Precinct precinctName1, Precinct precinctName2) {
        return null;
    }


    public ResponseEntity mergePrecinct(Precinct canonName1, Precinct canonName2) {
        return null;
    }

    public ResponseEntity remove(Precinct precinct1) {
        return null;
    }



}
