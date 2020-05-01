package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity(name = "STATES")
public class State extends Region {

    Set<String> districtCNames;

    @ElementCollection
    @Column(name = "child_district")
    public Set<String> getDistrictCNames() {
        return this.districtCNames;
    }

    public void setDistrictCNames(Set<String> district) {
        this.districtCNames = district;
    }
}
