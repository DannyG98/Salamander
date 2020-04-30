package com.teammander.salamander.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "DEMOGRAPHIC_DATA")
public class DemographicData {
    int demographicDataID;
    int whitePop;
    int blackPop;
    int asianPop;
    int otherPop;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int getDemographicDataID() {
        return demographicDataID;
    }

    public void setDemographicDataID(int demographicDataID) {
        this.demographicDataID = demographicDataID;
    }

    @Column(name = "white_pop")
    public int getWhitePop() {
        return whitePop;
    }

    public void setWhitePop(int whitePop) {
        this.whitePop = whitePop;
    }

    @Column(name = "black_pop")
    public int getBlackPop() {
        return blackPop;
    }

    public void setBlackPop(int blackPop) {
        this.blackPop = blackPop;
    }

    @Column(name = "asian_pop")
    public int getAsianPop() {
        return asianPop;
    }

    public void setAsianPop(int asianPop) {
        this.asianPop = asianPop;
    }

    @Column(name = "other_pop")
    public int getOtherPop() {
        return otherPop;
    }

    public void setOtherPop(int otherPop) {
        this.otherPop = otherPop;
    }
}
