package com.teammander.salamander.controller;

import java.util.ArrayList;
import java.util.List;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mil.nga.sf.geojson.Geometry;


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

    @GetMapping("/modifyNeighbor")
    public ResponseEntity<String> addNeighbor(@RequestParam String p1, @RequestParam String p2, @RequestParam String op) {
        if (op.equals("add")) {
            getPs().addNeighbor(p1, p2);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (op.equals("delete")) {
            getPs().deleteNeighbor(p1, p2);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(ControllerErrors.badQueryMsg("op", op), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/mergePrecinct")
    public Precinct mergePrecinct(@RequestParam String p1, @RequestParam String p2) {
        return getPs().mergePrecincts(p1, p2);
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

    @PostMapping("/updateDemoData")
    public ResponseEntity<?> updateDemoData(@RequestParam String pCName, @RequestBody DemographicData demoData) {
        Precinct targetPrecinct = getPs().updateDemoData(pCName, demoData);
        if (targetPrecinct == null)
            return new ResponseEntity<>(ControllerErrors.unableToFindMsg(pCName), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(targetPrecinct, HttpStatus.OK);
    }

    @PostMapping("/updateBoundary")
    public ResponseEntity<?> updateBoundary(@RequestParam String pCName, @RequestBody Geometry geometry) {
        Precinct targetPrecinct = getPs().updateBoundary(pCName, geometry);
        if (targetPrecinct == null)
            return new ResponseEntity<>(ControllerErrors.unableToFindMsg(pCName), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(targetPrecinct, HttpStatus.OK);
    }

    @PostMapping("/updateElecData")
    public ResponseEntity<?> updateElecData(@RequestParam String pCName, @RequestBody ElectionData elecData) {
        Precinct targetPrecinct = getPs().updateElectionData(pCName, elecData);
        if (targetPrecinct == null)
            return new ResponseEntity<>(ControllerErrors.unableToFindMsg(pCName), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(targetPrecinct, HttpStatus.OK);
    }

    // Below are post requests for uploading to server. Should not be used except for dev purposes.
    // An idea can be to place a 'key' within the post body to verify authenticity and rely upon
    // HTTPS encrypting and get a HTTPS certificate from a CA...

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
