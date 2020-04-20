package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

public class District extends Region{

    String stateCName;
    Set<String> precinctCNames;

    public District(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, String aState, Set<String> aPrecinct) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.stateCName = aState;
        this.precinctCNames= aPrecinct;
    }

    public String getStateCName() {
        return stateCName;
    }

    public void setStateCName(String state) {
        this.stateCName = state;
    }

    public Set<String> getPrecinctCNames() {
        return this.precinctCNames;
    }

    public void setPrecinctCNames(Set<String> precinct) {
        this.precinctCNames = precinct;
    }
}
