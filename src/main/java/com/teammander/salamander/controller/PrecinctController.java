package com.teammander.salamander.controller;

import java.util.ArrayList;
import java.util.List;

import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/getPrecinct/{precinctCanonName}")
    public Precinct getPrecinct(@PathVariable String canonName) {
        return getPs().getPrecinct(canonName);
    }

    @GetMapping("/addNeighbor?p1={precinctName1}&p2={precinctName2}")
    public void addNeighbor(@PathVariable String precinctName1, @PathVariable String precinctName2) {
        getPs().addNeighbor(precinctName1, precinctName2);;
    }

    @GetMapping("/deleteNeighbor?p1={precinctName1}&p2={precinctName2}")
    public void deleteNeighbor(String precinctName1, String precinctName2) {
        getPs().deleteNeighbor(precinctName1, precinctName2);
    }

    @GetMapping("/mergePrecinct?p1={precinctName1}&p2={precinctName2}")
    public Precinct mergePrecinct(String precinctName1, String precinctName2) {
        return getPs().mergePrecincts(precinctName1, precinctName2);
    }

    @GetMapping("/removePrecinct/{precinct1}")
    public void remove(String precinct1) {
        getPs().remove(precinct1);
    }

    @PostMapping("/getMultiplePrecincts")
    public List<Precinct> getMultiplePrecincts(@RequestBody List<String> query) {
        List<Precinct> queryResponse = new ArrayList<>();
        for (String s : query) {
            queryResponse.add(getPs().getPrecinct(s));
        }
        return queryResponse;
    }

    @PostMapping("/uploadPrecinct")
    public void uploadPrecinct(@RequestBody Precinct precinct) {
        getPs().insertPrecinct(precinct);
    }
    
    @PostMapping("/multiUploadPrecincts")
    public void multiUploadPrecincts(@RequestBody List<Precinct> precincts) {
        for (Precinct p : precincts)
            getPs().insertPrecinct(p);
    }

}
