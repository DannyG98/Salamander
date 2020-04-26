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

@Entity(name = "DISTRICT_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class District extends Region{

    String stateCName;
    Set<String> precinctCNames;

    @Column(name = "PARENT")
    public String getStateCName() {
        return this.stateCName;
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
