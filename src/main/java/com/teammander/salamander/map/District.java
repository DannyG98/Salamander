package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DISTRICT_TABLE")
public class District extends Region{

    String stateCName;
    Set<String> precinctCNames;

    // public District(String canonName, String displayName, Geometry geometry, DemographicData demoData, ElectionData elecData, String aState, Set<String> aPrecinct) {
    //     super(canonName, displayName, geometry, demoData, elecData);
    //     this.stateCName = aState;
    //     this.precinctCNames= aPrecinct;
    // }

    @Column(name = "PARENT")
    public String getStateCName() {
        return stateCName;
    }

    public void setStateCName(String state) {
        this.stateCName = state;
    }

    @ElementCollection
    @Column(name="CHILDREN")
    public Set<String> getPrecinctCNames() {
        return this.precinctCNames;
    }

    public void setPrecinctCNames(Set<String> precinct) {
        this.precinctCNames = precinct;
    }
}
