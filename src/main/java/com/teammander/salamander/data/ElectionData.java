package com.teammander.salamander.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ELECTION_DATA_TABLE")
public class ElectionData {

    int electionDataId;
    List<Election> elections;

    // public ElectionData() {
    //     this.elections = new List<>();
    // }

    @Id
    @GeneratedValue
    public int getElectionDataId() {
        return electionDataId;
    }

    public void setElectionDataId(int electionDataId) {
        this.electionDataId = electionDataId;
    }

    @OneToMany
    public List<Election> getElections() {
        return this.elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }

    public Election findElection(ElectionType type, Year year) {
        for (Election e : getElections()) {
            if (e.getYear() == year && e.getType() == type)
                return e;
        }
        return null;
    } 
}
