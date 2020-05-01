package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "PRECINCTS")
public class Precinct extends Region{

    String parentDistrictCName;
    String parentStateCName;
    Set<String> neighborCNames;
    PrecinctType type;

    public boolean isNeighbor(Precinct neighbor) {
        String neighCName = neighbor.getCanonName();
        Boolean ret = neighborCNames.contains(neighCName);
        return ret;
    }

    public void addNeighbor(Precinct neighbor) {
        String neighCName = neighbor.getCanonName();
        neighborCNames.add(neighCName);
    }

    public void deleteNeighbor(Precinct neighbor){
        String neighCName = neighbor.getCanonName();
        neighborCNames.remove(neighCName);
    }

    // TODO
    public Precinct merge(Precinct p1){
        return null;
    }

    @Column(name = "parent_district")
    public String getParentDistrictCName() {
        return this.parentDistrictCName;
    }

    public void setParentDistrictCName(String district) {
        this.parentDistrictCName = district;
    }

    @Column(name = "parent_state")
    public String getParentStateCName() {
        return this.parentStateCName;
    }

    public void setParentStateCName(String state) {
        this.parentStateCName = state;
    }

    @ElementCollection
    @Column(name = "neighbor_cname")
    public Set<String> getNeighborCNames() {
        return this.neighborCNames;
    }

    public void setNeighborCNames(Set<String> neighbors) {
        this.neighborCNames = neighbors;
    }

    @Enumerated(EnumType.STRING)
    public PrecinctType getType() {
        return this.type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
