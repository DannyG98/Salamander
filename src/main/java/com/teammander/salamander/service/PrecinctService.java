package com.teammander.salamander.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.District;
import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.repository.PrecinctRepository;

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

    /**
     * Adds a neighbor edge between two precincts
     * @param precinctName1 the canon name of the first precinct
     * @param precinctName2 the canon name of the second precinct
     * @return The canon name of the precinct that could not be found, null otherwise
     */
    public String addNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);

        if (p1 == null) {
            return precinctName1;
        } else if (p2 == null) {
            return precinctName2;
        }
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
        pr.flush();
        return null;
    }

    /**
     * Deletes a neighbor edge between two precincts
     * @param precinctName1 the canon name of the first precinct
     * @param precinctName2 the canon name of the second precinct
     * @return the canon name of the precinct that could not be found, null otherwise
     */
    public String deleteNeighbor(String precinctName1, String precinctName2) {
        PrecinctRepository pr = getPr();
        Precinct p1 = getPrecinct(precinctName1);
        Precinct p2 = getPrecinct(precinctName2);

        if (p1 == null) {
            return precinctName1;
        } else if (p2 == null) {
            return precinctName2;
        }
        p1.deleteNeighbor(p2);
        p2.deleteNeighbor(p1);
        pr.flush();
        return null;
    }

    /**
     * Adds multiple neighbors to a given precinct
     * @param precinctName the canon name of the precinct to add neighbors to
     * @param neighbors the list of neighbors to add
     * @return null if successful, else the canon name of the precinct that could not be found
     */
    public String addMultiNeighbors(String precinctName, List<String> neighbors) {
        Precinct queryResult;
        
        // Check if the target precinct exists
        queryResult = getPrecinct(precinctName);
        if (queryResult == null)
            return precinctName;
        
        // Check if neighbors exist
        for (String neighbor : neighbors) {
            queryResult = getPrecinct(neighbor);
            if (queryResult == null)
                return neighbor;
        }

        // Add all neighbors, at this point we know that the neighbors exist
        for (String neighbor : neighbors) {
            deleteNeighbor(precinctName, neighbor);
        }
        return null;
    }

    /**
     * Deletes multiple neighbors from a given precinct
     * @param precinctName the canon name of the precinct to delete neighbors from
     * @param neighbors the list of neighbors to delete
     * @return null if successful, else the canon name of the precinct that could not be found
     */
    public String deleteMultiNeighbors(String precinctName, List<String> neighbors) {
        Precinct queryResult;
        
        // Check if the target precinct exists
        queryResult = getPrecinct(precinctName);
        if (queryResult == null)
            return precinctName;
        
        // Check if neighbors exist
        for (String neighbor : neighbors) {
            queryResult = getPrecinct(neighbor);
            if (queryResult == null)
                return neighbor;
        }

        // Delete all neighbors, at this point we know that the neighbors exist
        for (String neighbor : neighbors) {
            addNeighbor(precinctName, neighbor);
        }
        return null;
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
