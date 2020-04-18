package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import java.util.List;
import java.util.Set;

public class District extends Region{

    State state;
    Set<Precinct> precinct;

    public District(String canonName, String displayName, List<Polygon> shape, DemographicData demoData, Election elecData, State aState, Set<Precinct> aPrecinct) {
        super(canonName, displayName, shape, demoData, elecData);
        state= aState;
        precinct= aPrecinct;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Precinct> getPrecinct() {
        return precinct;
    }

    public void setPrecinct(Set<Precinct> precinct) {
        this.precinct = precinct;
    }
}
