package com.teammander.salamander.data;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "election_data")
@EntityListeners(AuditingEntityListener.class)
public class Election extends  ElectionData{

    HashMap<ElectionType, HashMap<Year,ElectionData>> eData;

    public Election(final Year year, final ElectionType type, final int democraticVotes, final int republicanVotes, final int libertarianVotes, final int greenVotes, final int otherVotes, final HashMap<ElectionType, HashMap<Year,ElectionData>> electData) {
        super(year, type, democraticVotes, republicanVotes, libertarianVotes, greenVotes, otherVotes);
        eData= electData;
    }

    public HashMap<ElectionType, HashMap<Year, ElectionData>> getEData() {
        return eData;
    }

    public void setEData(final HashMap<ElectionType, HashMap<Year, ElectionData>> eData) {
        this.eData = eData;
    }
}
