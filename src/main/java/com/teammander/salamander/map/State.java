package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "states")
@EntityListeners(AuditingEntityListener.class)
public class State extends Region {

    Set<String> districtCanonNames;

    public State(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, Set<String> aDistrict) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.districtCanonNames= aDistrict;
    }

    @ElementCollection
    public Set<String> getDistrictCanonNames() {
        return districtCanonNames;
    }

    public void setDistrictCanonNames(Set<String> district) {
        this.districtCanonNames = district;
    }
}
