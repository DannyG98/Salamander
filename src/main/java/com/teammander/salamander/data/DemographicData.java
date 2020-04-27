package com.teammander.salamander.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "DEMO_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class DemographicData {
    int demographicDataID;
    int whitePop;
    int blackPop;
    int asianPop;
    int otherPop;

    // public DemographicData(int demographicDataID, int whitePop, int blackPop, int hispanicPop, int asianPop, int otherPop) {
    //     this.demographicDataID = demographicDataID;
    //     this.whitePop = whitePop;
    //     this.blackPop = blackPop;
    //     this.asianPop = asianPop;
    //     this.otherPop = otherPop;
    // }

    @Id
    @GeneratedValue
    public int getDemographicDataID() {
        return demographicDataID;
    }

    public void setDemographicDataID(int demographicDataID) {
        this.demographicDataID = demographicDataID;
    }

    @Column(name = "WHITE_POP")
    public int getWhitePop() {
        return whitePop;
    }

    public void setWhitePop(int whitePop) {
        this.whitePop = whitePop;
    }

    @Column(name = "BLACK_POP")
    public int getBlackPop() {
        return blackPop;
    }

    public void setBlackPop(int blackPop) {
        this.blackPop = blackPop;
    }

    @Column(name = "ASIAN_POP")
    public int getAsianPop() {
        return asianPop;
    }

    public void setAsianPop(int asianPop) {
        this.asianPop = asianPop;
    }

    @Column(name = "OTHER_POP")
    public int getOtherPop() {
        return otherPop;
    }

    public void setOtherPop(int otherPop) {
        this.otherPop = otherPop;
    }
}
