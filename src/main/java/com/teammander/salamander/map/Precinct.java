package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;
import com.teammander.salamander.data.ElectionData;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

//enum for type of errors
enum PrecinctType{
    GHOST, NORMAL, GAP;
}


public class Precinct extends Region{

    String parentDistrictCName;
    String parentStateCName;
    Set<String> neighborCNames;
    PrecinctType type;

    public Precinct(String canonName, String displayName, Geometry geometry, DemographicData demoData, ElectionData elecData, 
                        String dis, String aState, Set<String> neigh, PrecinctType pType) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.parentDistrictCName = dis;
        this.parentStateCName = aState;
        this.neighborCNames = neigh;
        this.type = pType;
    }

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

    public void updateDemoData(DemographicData demoData) {
        this.demoData = demoData;
    }

    public void updateElecData(ElectionData elecData) {
        this.elecData = elecData;
    }

    public void updateGeoemtry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getDistrictCName() {
        return parentDistrictCName;
    }

    public void setDistrictCName(String district) {
        this.parentDistrictCName = district;
    }

    public String getStateCName() {
        return parentStateCName;
    }

    public void setStateCName(String state) {
        this.parentStateCName = state;
    }

    public Set<String> getNeighborCNames() {
        return this.neighborCNames;
    }

    public void setNeighbors(Set<String> neighbors) {
        this.neighborCNames = neighbors;
    }

    public PrecinctType getType() {
        return type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
