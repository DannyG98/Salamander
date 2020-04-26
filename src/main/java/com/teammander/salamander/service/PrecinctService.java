package com.teammander.salamander.service;

import java.util.List;
import java.util.Optional;

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
        return this.pr;
    }

    public Precinct getPrecinct(String canonName) {
        PrecinctRepository pr = getPr();
        Optional<Precinct> queryResult = pr.findById(canonName);
        Precinct foundPrecint = queryResult.orElse(null);
        return foundPrecint;
    }

    public void rmPrecinct(Precinct precinct) {
        PrecinctRepository pr = getPr();
        pr.delete(precinct);
        pr.flush();
    }

    public void addNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
        pr.flush();
    }

    public void deleteNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);
        p1.deleteNeighbor(p2);
        p2.deleteNeighbor(p1);
        pr.flush();
    }

    // Returns the result of merge to controller
    public Precinct mergePrecincts(String canonName1, String canonName2) {
        return null;
    }

    public void remove(String precinctCanonName) {
        Precinct target = getPrecinct(precinctCanonName);
        if (target != null) {
            rmPrecinct(target);
        }
    }

    public Precinct updateDemoData(String pCName, DemographicData demoData) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = getPrecinct(pCName);
        if (targetPrecinct == null) {
            return null;
        }
        targetPrecinct.setDemoData(demoData);
        pr.flush();
        return targetPrecinct;
    }

    public Precinct updateBoundary(String pCName, Geometry geometry) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = getPrecinct(pCName);
        if (targetPrecinct == null) {
            return null;
        }
        targetPrecinct.setGeometry(geometry);
        pr.flush();
        return targetPrecinct;
    }

    public Precinct updateElectionData(String pCName, ElectionData eData) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = this.getPrecinct(pCName);
        if (targetPrecinct == null) {
            return null;
        }
        targetPrecinct.setElecData(eData);
        pr.flush();
        return targetPrecinct;
    }

    public void insertPrecinct(Precinct precinct) {
        PrecinctRepository pr = getPr();
        pr.saveAndFlush(precinct);
    }

    public void insertMultiplePrecincts(List<Precinct> precincts) {
        PrecinctRepository pr = getPr();
        pr.saveAll(precincts);
        pr.flush();
    }
}
