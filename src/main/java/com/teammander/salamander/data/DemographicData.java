package com.teammander.salamander.data;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "demographic_data")
@EntityListeners(AuditingEntityListener.class)
public class DemographicData {
    int demographicDataID;
    int whitePop;
    int blackPop;
    int hispanicPop;
    int asianPop;
    int otherPop;

    public DemographicData(int demographicDataID, int whitePop, int blackPop, int hispanicPop, int asianPop, int otherPop) {
        this.demographicDataID = demographicDataID;
        this.whitePop = whitePop;
        this.blackPop = blackPop;
        this.hispanicPop = hispanicPop;
        this.asianPop = asianPop;
        this.otherPop = otherPop;
    }

    @Id
    @GeneratedValue
    public int getDemographicDataID() {
        return demographicDataID;
    }

    public void setDemographicDataID(int demographicDataID) {
        this.demographicDataID = demographicDataID;
    }

    public int getWhitePop() {
        return whitePop;
    }

    public void setWhitePop(int whitePop) {
        this.whitePop = whitePop;
    }

    public int getBlackPop() {
        return blackPop;
    }

    public void setBlackPop(int blackPop) {
        this.blackPop = blackPop;
    }

    public int getHispanicPop() {
        return hispanicPop;
    }

    public void setHispanicPop(int hispanicPop) {
        this.hispanicPop = hispanicPop;
    }

    public int getAsianPop() {
        return asianPop;
    }

    public void setAsianPop(int asianPop) {
        this.asianPop = asianPop;
    }

    public int getOtherPop() {
        return otherPop;
    }

    public void setOtherPop(int otherPop) {
        this.otherPop = otherPop;
    }
}
