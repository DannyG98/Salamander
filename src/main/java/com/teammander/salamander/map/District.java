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
@Table(name = "districts")
@EntityListeners(AuditingEntityListener.class)
public class District extends Region{

    String stateCanonicalName;
    Set<String> precinctCanonNames;

    public District(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, String aState, Set<String> aPrecinct) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.stateCanonicalName = aState;
        this.precinctCanonNames= aPrecinct;
    }

    public String getStateCanonicalName() {
        return this.stateCanonicalName;
    }

    public void setStateCanonicalName(String state) {
        this.stateCanonicalName = state;
    }

    @ElementCollection
    public Set<String> getPrecinct() {
        return this.precinctCanonNames;
    }

    public void setPrecinct(Set<String> precinct) {
        this.precinctCanonNames = precinct;
    }
}
