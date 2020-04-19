package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;

import java.util.Set;

//enum for type of errors
enum PrecinctType{
    GHOST, NORMAL, GAP;
}


public class Precinct extends Region{

    District district;
    State state;
    Set<String> neighborCanonNames;
    PrecinctType type;

    public Precinct(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData, District dis, State aState, Set<String> neigh, PrecinctType pType) {
        super(canonName, displayName, geometry, demoData, elecData);
        this.district = dis;
        this.state = aState;
        this.neighborCanonNames = neigh;
        this.type = pType;
    }

    public boolean isNeighbor(Precinct neighbor){
        return false;
    }

    public void addNeighbor(Precinct neighbor){

    }

    public void deleteNeighbor(Precinct neighbor){

    }

    public Precinct merge(Precinct p1){
        return null;
    }

    public void updatePrecinct(){

    }

    public void updateDemoData(DemographicData demoData){

    }

    public void updateElecData(Election elecData){

    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<String> getNeighbors() {
        return this.neighborCanonNames;
    }

    public void setNeighbors(Set<String> neighbors) {
        this.neighborCanonNames = neighbors;
    }

    public PrecinctType getType() {
        return type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
