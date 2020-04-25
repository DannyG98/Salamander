package com.teammander.salamander.data;

public abstract class Election {

    Year year;
    final ElectionType type;
    int democraticVotes;
    int republicanVotes;
    int libertarianVotes;
    int greenVotes;
    int otherVotes;

    public Election(Year year, ElectionType type, int democraticVotes, int republicanVotes, int libertarianVotes, int greenVotes, int otherVotes) {
        this.year = year;
        this.type = type;
        this.democraticVotes = democraticVotes;
        this.republicanVotes = republicanVotes;
        this.libertarianVotes = libertarianVotes;
        this.greenVotes = greenVotes;
        this.otherVotes = otherVotes;
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
