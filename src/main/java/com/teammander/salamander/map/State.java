package com.teammander.salamander.map;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity(name = "STATE_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class State extends Region {

    Set<String> districtCNames;

    // public State(String canonName, String displayName, Geometry geometry, DemographicData demoData, ElectionData elecData, Set<String> aDistrict) {
    //     super(canonName, displayName, geometry, demoData, elecData);
    //     this.districtCNames= aDistrict;
    // }

    @ElementCollection
    @Column(name = "CHILDREN")
    public Set<String> getDistrictCNames() {
        return this.districtCNames;
    }

    public void setDistrictCNames(Set<String> district) {
        this.districtCNames = district;
    }
}
