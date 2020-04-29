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
        return this.ps;
    }

    @GetMapping("/getPrecinct/{precinctCanonName}")
    public Precinct getPrecinct(@PathVariable String canonName) {
        PrecinctService ps = getPs();
        Precinct foundPrecinct = ps.getPrecinct(canonName);
        return foundPrecinct;
    }

    @GetMapping("/getAllPrecincts")
    public List<Precinct> getAllPrecincts() {
        PrecinctService ps = getPs();
        List<Precinct> foundPrecincts = ps.getAllPrecincts();
        return foundPrecincts;
    }

    @GetMapping("/modifyNeighbor")
    public ResponseEntity<String> addNeighbor(@RequestParam String p1, 
            @RequestParam String p2, @RequestParam String op) {
        PrecinctService ps = getPs();
        if (op.equals("add")) {
            ResponseEntity<String> re = ResponseEntity.ok(null);
            ps.addNeighbor(p1, p2);
            return re;
        }
        else if (op.equals("delete")) {
            ResponseEntity<String> re = ResponseEntity.ok(null);
            ps.deleteNeighbor(p1, p2);
            return re;
        }
        else {
            String errMsg = ControllerErrors.badQueryMsg("op", op);
            ResponseEntity<String> re = new ResponseEntity<>(errMsg,
                HttpStatus.BAD_REQUEST);
            return re;
        }
    }

    @GetMapping("/mergePrecinct")
    public Precinct mergePrecinct(@RequestParam String p1, @RequestParam String p2) {
        PrecinctService ps = getPs();
        Precinct mergedPrecinct = ps.mergePrecincts(p1, p2);
        return mergedPrecinct;
    }

    @GetMapping("/removePrecinct/{precinct1}")
    public void remove(String precinct1) {
        PrecinctService ps = getPs();
        ps.remove(precinct1);
    }

    @PostMapping("/getMultiplePrecincts")
    public List<Precinct> getMultiplePrecincts(@RequestBody List<String> query) {
        PrecinctService ps = getPs();
        List<Precinct> queryResponse = new ArrayList<>();
        for (String s : query) {
            Precinct foundPrecinct = ps.getPrecinct(s); 
            queryResponse.add(foundPrecinct);
        }
        return queryResponse;
    }

    @PostMapping("/updateDemoData")
    public ResponseEntity<?> updateDemoData(@RequestParam String pCName, 
            @RequestBody DemographicData demoData) {
        PrecinctService ps = getPs();
        Precinct targetPrecinct = ps.updateDemoData(pCName, demoData);
        if (targetPrecinct == null) {
            String errMsg = ControllerErrors.unableToFindMsg(pCName);
            ResponseEntity<String> re = new ResponseEntity<>(errMsg, HttpStatus.NOT_FOUND);
            return re;
        }
        ResponseEntity<Precinct> re = ResponseEntity.ok(targetPrecinct);
        return re;
    }

    @PostMapping("/updateBoundary")
    public ResponseEntity<?> updateBoundary(@RequestParam String pCName, @RequestBody Geometry geometry) {
        Precinct targetPrecinct = getPs().updateBoundary(pCName, geometry);
        if (targetPrecinct == null) {
            String errMsg = ControllerErrors.unableToFindMsg(pCName);
            ResponseEntity<String> re = new ResponseEntity<>(errMsg, HttpStatus.NOT_FOUND);
            return re;
        }
        ResponseEntity<Precinct> re = ResponseEntity.ok(targetPrecinct);
        return re;
    }

    @PostMapping("/updateElecData")
    public ResponseEntity<?> updateElecData(@RequestParam String pCName, @RequestBody ElectionData elecData) {
        Precinct targetPrecinct = getPs().updateElectionData(pCName, elecData);
        if (targetPrecinct == null) {
            String errMsg = ControllerErrors.unableToFindMsg(pCName);
            ResponseEntity<String> re = new ResponseEntity<>(errMsg, HttpStatus.NOT_FOUND);
            return re;
        }
        return new ResponseEntity<>(targetPrecinct, HttpStatus.OK);
    }

    /* ONLY FOR DEV USE REMOVE FOR FINAL BUILD **/

    @PostMapping("/uploadPrecinct")
    public void uploadPrecinct(@RequestBody Precinct precinct) {
        PrecinctService ps = getPs();
        ps.insertPrecinct(precinct, true);
    }
    
    @PostMapping("/multiUploadPrecincts")
    public void multiUploadPrecincts(@RequestBody List<Precinct> precincts) {
        PrecinctService ps = getPs();
        ps.insertMultiplePrecincts(precincts);
    }

}
