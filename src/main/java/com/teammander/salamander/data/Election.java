package com.teammander.salamander.data;

import java.util.HashMap;

public class Election extends  ElectionData{

    HashMap<ElectionType, HashMap<Year,ElectionData>> eData;

    public Election(Year year, ElectionType type, int democraticVotes, int republicanVotes, int libertarianVotes, int greenVotes, int otherVotes, HashMap<ElectionType, HashMap<Year,ElectionData>> electData) {
        super(year, type, democraticVotes, republicanVotes, libertarianVotes, greenVotes, otherVotes);
        eData= electData;
    }

    public HashMap<ElectionType, HashMap<Year, ElectionData>> geteData() {
        return eData;
    }

    public void setData(HashMap<ElectionType, HashMap<Year, ElectionData>> eData) {
        this.eData = eData;
    }
}
