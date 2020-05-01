package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity(name = "DISTRICTS")
public class District extends Region{

    String stateCName;
    Set<String> precinctCNames;

    @Column(name = "parent_state")
    public String getStateCName() {
        return this.stateCName;
    }

    public void setStateCName(String state) {
        this.stateCName = state;
    }

    @ElementCollection
    @Column(name="child_precinct")
    public Set<String> getPrecinctCNames() {
        return this.precinctCNames;
    }

    public void setPrecinctCNames(Set<String> precinct) {
        this.precinctCNames = precinct;
    }

    public void addPrecinctChild(String childName) {
        Set<String> children = getPrecinctCNames();
        children.add(childName);
    }

    public void removePrecinctChild(String childName) {
        Set<String> children = getPrecinctCNames();
        children.remove(childName);
    }
}
