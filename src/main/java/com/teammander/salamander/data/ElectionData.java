package com.teammander.salamander.data;

import java.util.HashMap;

public class ElectionData {

    HashMap<ElectionType, HashMap<Year,Election>> elections;

    public ElectionData() {
        this.elections = new HashMap<>();
    }

    public HashMap<ElectionType, HashMap<Year, Election>> getElections() {
        return this.elections;
    }

    public void setElections(HashMap<ElectionType, HashMap<Year, Election>> elections) {
        this.elections = elections;
    }

    public Election findElection(ElectionType type, Year year) {
        return getElections().get(type).get(year);
    } 
}
