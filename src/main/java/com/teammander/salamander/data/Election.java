package com.teammander.salamander.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "ELECTION_TABLE")
@EntityListeners(AuditingEntityListener.class)
public abstract class Election {

    int electionId;
    Year year;
    ElectionType type;
    int democraticVotes;
    int republicanVotes;
    int libertarianVotes;
    int greenVotes;
    int otherVotes;

    // public Election(Year year, ElectionType type, int democraticVotes, int republicanVotes, int libertarianVotes, int greenVotes, int otherVotes) {
    //     this.year = year;
    //     this.type = type;
    //     this.democraticVotes = democraticVotes;
    //     this.republicanVotes = republicanVotes;
    //     this.libertarianVotes = libertarianVotes;
    //     this.greenVotes = greenVotes;
    //     this.otherVotes = otherVotes;
    // }

    @Id
    @GeneratedValue
    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    @Enumerated(EnumType.STRING)
    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Enumerated(EnumType.STRING)
    public ElectionType getType() {
        return type;
    }

    public void setType(ElectionType type) {
        this.type = type;
    }

    @Column(name = "DEMO_VOTES")
    public int getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocraticVotes(int democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    @Column(name = "REPUB_VOTES")
    public int getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(int republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    @Column(name = "LIB_VOTES")
    public int getLibertarianVotes() {
        return libertarianVotes;
    }

    public void setLibertarianVotes(int libertarianVotes) {
        this.libertarianVotes = libertarianVotes;
    }

    @Column(name = "GREEN_VOTES")
    public int getGreenVotes() {
        return greenVotes;
    }

    public void setGreenVotes(int greenVotes) {
        this.greenVotes = greenVotes;
    }

    @Column(name = "OTHER_VOTES")
    public int getOtherVotes() {
        return otherVotes;
    }

    public void setOtherVotes(int otherVotes) {
        this.otherVotes = otherVotes;
    }
}
