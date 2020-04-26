package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PRECINCT_TABLE")
public class Precinct extends Region{

    String parentDistrictCName;
    String parentStateCName;
    Set<String> neighborCNames;
    PrecinctType type;

    // public Precinct(String canonName, String displayName, Geometry geometry, DemographicData demoData, ElectionData elecData, 
    //                     String dis, String aState, Set<String> neigh, PrecinctType pType) {
    //     super(canonName, displayName, geometry, demoData, elecData);
    //     this.parentDistrictCName = dis;
    //     this.parentStateCName = aState;
    //     this.neighborCNames = neigh;
    //     this.type = pType;
    // }

    public boolean isNeighbor(Precinct neighbor){
        return neighborCNames.contains(neighbor.getCanonName());
    }

    public void addNeighbor(Precinct neighbor){
        neighborCNames.add(neighbor.getCanonName());
    }

    public void deleteNeighbor(Precinct neighbor){
        neighborCNames.remove(neighbor.getCanonName());
    }

    public Precinct merge(Precinct p1){
        return null;
    }

    @Column(name = "PARENT_DISTRICT")
    public String getParentDistrictCName() {
        return parentDistrictCName;
    }

    public void setParentDistrictCName(String district) {
        this.parentDistrictCName = district;
    }

    @Column(name = "PARENT_STATE")
    public String getParentStateCName() {
        return parentStateCName;
    }

    public void setParentStateCName(String state) {
        this.parentStateCName = state;
    }

    @ElementCollection
    @Column(name = "NEIGHBORS")
    public Set<String> getNeighborCNames() {
        return this.neighborCNames;
    }

    public void setNeighborCNames(Set<String> neighbors) {
        this.neighborCNames = neighbors;
    }

    @Enumerated(EnumType.STRING)
    public PrecinctType getType() {
        return type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
