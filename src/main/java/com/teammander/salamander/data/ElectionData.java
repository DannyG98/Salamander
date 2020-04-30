package com.teammander.salamander.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "ELECTION_DATA")
public class ElectionData {

    int electionDataId;
    List<Election> elections;

    @Id
    @GeneratedValue
    public int getElectionDataId() {
        return electionDataId;
    }

    public void setElectionDataId(int electionDataId) {
        this.electionDataId = electionDataId;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Election> getElections() {
        return this.elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }

    public Election findElection(ElectionType type, Year year) {
        for (Election e : getElections()) {
            if (e.getYear() == year && e.getType() == type) {
                return e;
            }
        }
        return null;
    } 
}
