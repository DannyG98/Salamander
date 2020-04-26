package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
        return districtCNames;
    }

    public void setDistrictCNames(Set<String> district) {
        this.districtCNames = district;
    }
}
