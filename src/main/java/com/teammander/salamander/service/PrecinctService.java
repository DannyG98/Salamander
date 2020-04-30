package com.teammander.salamander.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.District;
import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.repository.DistrictRepository;
import com.teammander.salamander.repository.PrecinctRepository;
import com.teammander.salamander.repository.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mil.nga.sf.geojson.Geometry;

@Service
public class PrecinctService {
    PrecinctRepository pr;
    DistrictService ds;

    @Autowired
    public PrecinctService(PrecinctRepository pr, DistrictService ds) {
        this.pr = pr;
        this.ds = ds;
    }

    public PrecinctRepository getPr() {
        return this.pr;
    }

    public DistrictService getDs() {
        return this.ds;
    }

    public District getParentDistrict(Precinct precinct) {
        DistrictService ds = getDs();
        String districtCName = precinct.getParentDistrictCName();
        District parentDistrict = ds.getDistrict(districtCName);
        return parentDistrict;
    }

    public Precinct getPrecinct(String canonName) {
        PrecinctRepository pr = getPr();
        Optional<Precinct> queryResult = pr.findById(canonName);
        Precinct foundPrecint = queryResult.orElse(null);
        return foundPrecint;
    }

    public List<Precinct> getAllPrecincts() {
        PrecinctRepository pr = getPr();
        List<Precinct> allPrecincts = pr.findAll();
        return allPrecincts;
    }

    public void rmPrecinct(Precinct precinct) {
        PrecinctRepository pr = getPr();
        String targetCName = precinct.getCanonName();
        Set<String> neighbors = precinct.getNeighborCNames();
        District parent = getParentDistrict(precinct);

        for (String neighbor : neighbors) {
            deleteNeighbor(neighbor, targetCName);
        }
        parent.removePrecinctChild(targetCName);
        pr.delete(precinct);
        pr.flush();
    }

    public boolean addNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);

        if (p1 == null || p2 == null) {
            return false;
        }
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
        pr.flush();
        return true;
    }

    public boolean deleteNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);

        if (p1 == null || p2 == null) {
            return false;
        }
        p1.deleteNeighbor(p2);
        p2.deleteNeighbor(p1);
        pr.flush();
        return true;
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

    public void insertPrecinct(Precinct precinct, Boolean flush) {
        PrecinctRepository pr = getPr();
        String targetCName = precinct.getCanonName();
        Set<String> neighbors = precinct.getNeighborCNames();

        for (String neighbor : neighbors) {
            addNeighbor(neighbor, targetCName);
        }
        pr.save(precinct);
        if (flush) {
            pr.flush();
        }
    }

    public void insertMultiplePrecincts(List<Precinct> precincts) {
        PrecinctRepository pr = getPr();
        for (Precinct p : precincts) {
            insertPrecinct(p, false);
        }
        pr.flush();
    }
}
