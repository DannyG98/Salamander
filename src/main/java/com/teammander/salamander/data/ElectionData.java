package com.teammander.salamander.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//enum for year of election
enum Year{
    SIXTEEN(2016), EIGHTEEN(2018);
    int value;

    Year(int val) {
        value=val;
    }

    public int getValue() {
        return value;
    }
}


//enum for type of election
enum ElectionType{
    PRESIDENTIAL, CONGRESSIONAL
}

@MappedSuperclass
public abstract class ElectionData {

    int electionDataId;
    Year year;
    ElectionType type;
    int democraticVotes;
    int republicanVotes;
    int libertarianVotes;
    int greenVotes;
    int otherVotes;

    public ElectionData(Year year, ElectionType type, int democraticVotes, int republicanVotes, int libertarianVotes, int greenVotes, int otherVotes) {
        this.year = year;
        this.type = type;
        this.democraticVotes = democraticVotes;
        this.republicanVotes = republicanVotes;
        this.libertarianVotes = libertarianVotes;
        this.greenVotes = greenVotes;
        this.otherVotes = otherVotes;
    }

    @Id
    @GeneratedValue
    public int getElectionDataID() {
        return this.electionDataId;
    }

    public void setElectionDataID(int id) {
        this.electionDataId = id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public ElectionType getType() {
        return type;
    }

    public void setType(ElectionType type) {
        this.type = type;
    }

    public int getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocraticVotes(int democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    public int getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(int republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public int getLibertarianVotes() {
        return libertarianVotes;
    }

    public void setLibertarianVotes(int libertarianVotes) {
        this.libertarianVotes = libertarianVotes;
    }

    public int getGreenVotes() {
        return greenVotes;
    }

    public void setGreenVotes(int greenVotes) {
        this.greenVotes = greenVotes;
    }

    public int getOtherVotes() {
        return otherVotes;
    }

    public void setOtherVotes(int otherVotes) {
        this.otherVotes = otherVotes;
    }
}
