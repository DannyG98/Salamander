package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

public class District extends Region{

    State state;
    Set<String> precinctCanonNames;

    public District(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, State aState, Set<String> aPrecinct) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.state = aState;
        this.precinctCanonNames= aPrecinct;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<String> getPrecinct() {
        return this.precinctCanonNames;
    }

    public void setPrecinct(Set<String> precinct) {
        this.precinctCanonNames = precinct;
    }
}
