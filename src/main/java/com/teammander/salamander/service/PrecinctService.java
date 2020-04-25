package com.teammander.salamander.service;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mil.nga.sf.geojson.Geometry;

@Service
public class PrecinctService {
    PrecinctRepository pr;

    @Autowired
    public PrecinctService(PrecinctRepository pr) {
        this.pr = pr;
    }

    public PrecinctRepository getPr() {
        return pr;
    }

    public Precinct getPrecinct(String canonName) {
        return getPr().findPrecinct(canonName);
    }

    public void rmPrecinct(Precinct precinct) {
        getPr().rmPrecinct(precinct);
    }

    public void addNeighbor(String precinctName1, String precinctName2) {
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
    }

    public void deleteNeighbor(String precinctName1, String precinctName2) {
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);
        p1.deleteNeighbor(p2);
        p2.deleteNeighbor(p1);
    }

    // Returns the result of merge to controller
    public Precinct mergePrecincts(String canonName1, String canonName2) {
        return null;
    }

    public void remove(String precinctCanonName) {
        Precinct target = getPrecinct(precinctCanonName);
        getPr().rmPrecinct(target);
    }

    public void insertPrecinct(Precinct precinct) {
        getPr().insertPrecinct(precinct);
    }

    public Precinct updateDemoData(String pCName, DemographicData demoData) {
        Precinct targetPrecinct = this.getPrecinct(pCName);
        if (targetPrecinct == null)
            return null;
        targetPrecinct.updateDemoData(demoData);
        return targetPrecinct;
    }

    public Precinct updateBoundary(String pCName, Geometry geometry) {
        Precinct targetPrecinct = this.getPrecinct(pCName);
        if (targetPrecinct == null)
            return null;
        targetPrecinct.updateGeoemtry(geometry);
        return targetPrecinct;
    }

    public Precinct updateElectionData(String pCName, ElectionData eData) {
        Precinct targetPrecinct = this.getPrecinct(pCName);
        if (targetPrecinct == null)
            return null;
        targetPrecinct.updateElecData(eData);
        return targetPrecinct;
    }
}
