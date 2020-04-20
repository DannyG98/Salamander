package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

public class State extends Region {

    Set<String> districtCNames;

    public State(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, Set<String> aDistrict) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.districtCNames= aDistrict;
    }

    public Set<String> getDistrictCNames() {
        return districtCNames;
    }

    public void setDistrictCNames(Set<String> district) {
        this.districtCNames = district;
    }
}
