package com.teammander.salamander.map;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity(name = "PRECINCT_TABLE")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "PARENT_DISTRICT")
    public String getParentDistrictCName() {
        return this.parentDistrictCName;
    }

    public void setParentDistrictCName(String district) {
        this.parentDistrictCName = district;
    }

    @Column(name = "PARENT_STATE")
    public String getParentStateCName() {
        return this.parentStateCName;
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
        return this.type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
