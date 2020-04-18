package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import java.util.List;
import java.util.Set;

public class State extends Region {

    Set<District> district;

    public State(String canonName, String displayName, List<Polygon> shape, DemographicData demoData, Election elecData, Set<District> aDistrict) {
        super(canonName, displayName, shape, demoData, elecData);
        district= aDistrict;
    }

    public Set<District> getDistrict() {
        return district;
    }

    public void setDistrict(Set<District> district) {
        this.district = district;
    }
}
