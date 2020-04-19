package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

public class State extends Region {

    Set<String> district;

    public State(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, Set<String> aDistrict) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.district= aDistrict;
    }

    public Set<String> getDistrictList() {
        return district;
    }

    public void setDistrict(Set<String> district) {
        this.district = district;
    }
}
