package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import mil.nga.sf.geojson.Geometry;

import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//enum for type of errors
enum PrecinctType{
    GHOST, NORMAL, GAP;
}

@Entity
@Table(name = "precincts")
@EntityListeners(AuditingEntityListener.class)
public class Precinct extends Region {

    String districtCanonicalName;
    String stateCanonicalName;
    List<String> neighborCanonNames;
    PrecinctType type;

    public Precinct(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, String dis, String aState, List<String> neigh, PrecinctType pType) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.districtCanonicalName = dis;
        this.stateCanonicalName = aState;
        this.neighborCanonNames = neigh;
        this.type = pType;
    }

    public boolean isNeighbor(Precinct neighbor) {
        return false;
    }

    public void addNeighbor(Precinct neighbor) {

    }

    public void deleteNeighbor(Precinct neighbor) {

    }

    public Precinct merge(Precinct p1) {
        return null;
    }

    public void updatePrecinct() {

    }

    public void updateDemoData(DemographicData demoData) {

    }

    public void updateElecData(Election elecData) {

    }

    public String getdistrictCanonicalName() {
        return districtCanonicalName;
    }

    public void setDistrictCanonicalName(String district) {
        this.districtCanonicalName = district;
    }

    public String getStateCanonicalName() {
        return stateCanonicalName;
    }

    public void setStateCanonicalName(String state) {
        this.stateCanonicalName = state;
    }

    @ElementCollection
    // @CollectionTable(name="neighbors", joinColumns = @JoinColumn(name="canonName"))
    // @Column(name = "neighbor_canon_name")
    public List<String> getNeighborCanonNames() {
        return this.neighborCanonNames;
    }

    public void setNeighborCanonNames(List<String> neighbors) {
        this.neighborCanonNames = neighbors;
    }

    @Enumerated(EnumType.STRING)
    public PrecinctType getType() {
        return type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
