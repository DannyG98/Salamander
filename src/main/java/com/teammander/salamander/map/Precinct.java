package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity(name = "PRECINCTS")
public class Precinct extends Region{

    District parentDistrict;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_district")
    public District getParentDistrict() {
        return this.parentDistrict;
    }

    public void setParentDistrict(District district) {
        this.parentDistrict = district;
    }

    @Lob
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
