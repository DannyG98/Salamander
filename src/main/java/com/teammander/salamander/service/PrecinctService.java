package com.teammander.salamander.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.data.ElectionType;
import com.teammander.salamander.data.Year;
import com.teammander.salamander.map.District;
import com.teammander.salamander.map.Precinct;
import com.teammander.salamander.repository.ElectionRepository;
import com.teammander.salamander.repository.PrecinctRepository;
import com.teammander.salamander.transaction.Transaction;
import com.teammander.salamander.transaction.TransactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrecinctService {
    PrecinctRepository pr;
    ElectionRepository er;
    DistrictService ds;
    TransactionService ts;

    @Autowired
    public PrecinctService(PrecinctRepository pr, ElectionRepository er, DistrictService ds, TransactionService ts) {
        this.pr = pr;
        this.er = er;
        this.ds = ds;
        this.ts = ts;
    }

    public PrecinctRepository getPr() {
        return this.pr;
    }

    public ElectionRepository getEr() {
        return this.er;
    }

    public DistrictService getDs() {
        return this.ds;
    }

    public TransactionService getTs() {
        return this.ts;
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
        District parent = precinct.getParentDistrict();

        for (String neighbor : neighbors) {
            deleteNeighbor(neighbor, targetCName);
        }
        parent.removePrecinctChild(precinct);
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

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        String p1Display = p1.getDisplayName();
        String p2Display = p2.getDisplayName();
        nTrans.setTransType(TransactionType.CHANGE_NEIGHBOR);
        nTrans.setBefore(p1Display + "<-/->" + p2Display);
        nTrans.setAfter(p1Display + " <--> " + p2Display);
        nTrans.setWhoCanon(precinctName1 + ", " + precinctName2);
        nTrans.setWhoDisplay(p1Display + ", " + p2Display);
        nTrans.setWhat("Neighborship");
        ts.addTransaction(nTrans);

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

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        String p1Display = p1.getDisplayName();
        String p2Display = p2.getDisplayName();
        nTrans.setTransType(TransactionType.CHANGE_NEIGHBOR);
        nTrans.setBefore(p1Display + " <--> " + p2Display);
        nTrans.setAfter(p1Display + "<-/->" + p2Display);
        nTrans.setWhoCanon(precinctName1 + ", " + precinctName2);
        nTrans.setWhoDisplay(p1Display + ", " + p2Display);
        nTrans.setWhat("Neighborship");
        ts.addTransaction(nTrans);

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
            addNeighbor(precinctName, neighbor);
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
            deleteNeighbor(precinctName, neighbor);
        }

        return null;
    }


    // Returns the result of merge to controller
    public Precinct mergePrecincts(List<String> precinctNames) {
        PrecinctRepository pr = getPr();
        List<Precinct> precincts = pr.findAllById(precinctNames);
        Precinct mergedPrecinct = Precinct.mergePrecincts(precincts);
        pr.deleteAll(precincts);
        pr.saveAndFlush(mergedPrecinct);
        return mergedPrecinct;
    }

    public void remove(String precinctCanonName) {
        Precinct target = getPrecinct(precinctCanonName);
        if (target != null) {
            rmPrecinct(target);
        }
    }

    public Precinct updateDemoData(String pCName, int demoId, String field, int newVal) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = getPrecinct(pCName);
        int beforeVal;

        if (targetPrecinct == null) {
            return null;
        }

        DemographicData targetDD = targetPrecinct.getDemoData();
        if (field.equals("whitePop")) {
            beforeVal = targetDD.getWhitePop();
            targetDD.setWhitePop(newVal);
        }
        else if (field.equals("blackPop")) {
            beforeVal = targetDD.getBlackPop();
            targetDD.setBlackPop(newVal);
        }
        else if (field.equals("asianPop")) {
            beforeVal = targetDD.getAsianPop();
            targetDD.setAsianPop(newVal);
        }
        else if (field.equals("otherPop")) {
            beforeVal = targetDD.getOtherPop();
            targetDD.setOtherPop(newVal);
        } else {
            throw new IllegalArgumentException(field);
        }

        pr.flush();

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        nTrans.setTransType(TransactionType.CHANGE_DEMODATA);
        nTrans.setWhoCanon(targetPrecinct.getCanonName());
        nTrans.setWhoDisplay(targetPrecinct.getDisplayName());
        nTrans.setWhat(field);
        nTrans.setBefore(Integer.toString(beforeVal));
        nTrans.setAfter(Integer.toString(newVal));
        ts.addTransaction(nTrans);

        return targetPrecinct;
    }

    public Precinct updateBoundary(String pCName, String geometry) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = getPrecinct(pCName);

        if (targetPrecinct == null) {
            return null;
        }
        String oldGeometry = targetPrecinct.getGeometry();
        targetPrecinct.setGeometry(geometry);
        pr.flush();

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        nTrans.setTransType(TransactionType.CHANGE_BOUNDARY);
        nTrans.setWhoCanon(targetPrecinct.getCanonName());
        nTrans.setWhoDisplay(targetPrecinct.getDisplayName());
        nTrans.setWhat("Boundary Data");
        nTrans.setBefore(oldGeometry);
        nTrans.setAfter(geometry);
        ts.addTransaction(nTrans);

        return targetPrecinct;
    }

    public Precinct updateElection(String pCName, int eid, String field, int newVal) {
        PrecinctRepository pr = getPr();
        Precinct targetPrecinct = this.getPrecinct(pCName);
        if (targetPrecinct == null) {
            return null;
        }

        Election elec = targetPrecinct.findElection(eid);
        int beforeVal;
        if (field.equals("democraticVotes")) {
            beforeVal = elec.getDemocraticVotes();
            elec.setDemocraticVotes(newVal); 
        }
        else if (field.equals("republicanVotes")) {
            beforeVal = elec.getRepublicanVotes();
            elec.setRepublicanVotes(newVal);
        }
        else if (field.equals("libertarianVotes")) {
            beforeVal = elec.getLibertarianVotes();
            elec.setLibertarianVotes(newVal);
        }
        else if (field.equals("greenVotes")) {
            beforeVal = elec.getGreenVotes();
            elec.setGreenVotes(newVal);
        }
        else if (field.equals("otherVotes")) {
            beforeVal = elec.getOtherVotes();
            elec.setOtherVotes(newVal);
        }
        else {
            throw new IllegalArgumentException(field);
        }

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        String whatString = String.format("%s %s %s", elec.getYear(), elec.getType(), field);
        nTrans.setTransType(TransactionType.CHANGE_ELECDATA);
        nTrans.setWhoCanon(targetPrecinct.getCanonName());
        nTrans.setWhoDisplay(targetPrecinct.getDisplayName());
        nTrans.setWhat(whatString);
        nTrans.setBefore(Integer.toString(beforeVal));
        nTrans.setAfter(Integer.toString(newVal));
        ts.addTransaction(nTrans);

        pr.flush();
        return targetPrecinct;
    }

    public Precinct createNewPrecinct(Precinct precinct, String parentName) {
        PrecinctRepository pr = getPr();
        DistrictService ds = getDs();
        District parentDistrict = ds.getDistrict(parentName);
        Random rand = new Random();
        ElectionData newED = new ElectionData();
        DemographicData newDD = new DemographicData();
        Election pres16 = new Election();
        Election cong16 = new Election();
        Election cong18 = new Election();

        pres16.setType(ElectionType.PRESIDENTIAL);
        pres16.setYear(Year.SIXTEEN);
        cong16.setType(ElectionType.CONGRESSIONAL);
        cong16.setYear(Year.SIXTEEN);
        cong18.setType(ElectionType.CONGRESSIONAL);
        cong18.setYear(Year.EIGHTEEN);
        newED.setElections(new ArrayList<>(Arrays.asList(pres16, cong16, cong18)));

        precinct.setElecData(newED);
        precinct.setDemoData(newDD);

        String canonName = String.format("ClientGenerated_%d",Math.abs(rand.nextLong()));
        while(pr.existsById(canonName)) {
            canonName = String.format("ClientGenerated_%d",Math.abs(rand.nextLong()));
        }
        precinct.setParentDistrict(parentDistrict);
        precinct.setCanonName(canonName);
        ds.insertChildPrecinct(parentName, precinct);
        pr.saveAndFlush(precinct);

        TransactionService ts = getTs();
        Transaction nTrans = new Transaction();
        nTrans.setTransType(TransactionType.NEW_PRECINCT);
        nTrans.setWhoCanon(precinct.getCanonName());
        nTrans.setWhoDisplay(precinct.getDisplayName());
        nTrans.setWhat("New Precinct");
        ts.addTransaction(nTrans);

        return precinct;
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
