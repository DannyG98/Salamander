package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import java.util.List;
import java.util.Set;

//enum for type of errors
enum PrecinctType{
    GHOST, NORMAL, GAP;
}


public class Precinct extends Region{

    District district;
    State state;
    Set<Precinct> neighbors;
    PrecinctType type;

    public Precinct(String canonName, String displayName, List<Polygon> shape, DemographicData demoData, Election elecData, District dis, State aState, Set <Precinct> neigh, PrecinctType pType) {
        super(canonName, displayName, shape, demoData, elecData);
        district= dis;
        state= aState;
        neighbors= neigh;
        type= pType;
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

    public Set<Precinct> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Precinct> neighbors) {
        this.neighbors = neighbors;
    }

    public PrecinctType getType() {
        return type;
    }

    public void setType(PrecinctType type) {
        this.type = type;
    }
}
