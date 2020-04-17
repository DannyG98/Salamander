package com.teammander.salamander.map;

import java.util.Set;

//enum for type of errors
enum ErrorType{
    DAT_ELECT_MISMATCH, DAT_DEMO_MISMATCH, PRCT_GAP, PRCT_OVERLAP, PRCT_OPEN, PRCT_ENCLOSE;
}

public class DataError {

    int eid;
    ErrorType eType;
    Set<Precinct> affectedPrct;
    Coordinate mapCoord;

    public DataError(int eid, ErrorType eType, Set<Precinct> affectedPrct, Coordinate mapCoord) {
        this.eid = eid;
        this.eType = eType;
        this.affectedPrct = affectedPrct;
        this.mapCoord = mapCoord;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public ErrorType geteType() {
        return eType;
    }

    public void seteType(ErrorType eType) {
        this.eType = eType;
    }

    public Set<Precinct> getAffectedPrct() {
        return affectedPrct;
    }

    public void setAffectedPrct(Set<Precinct> affectedPrct) {
        this.affectedPrct = affectedPrct;
    }

    public Coordinate getMapCoord() {
        return mapCoord;
    }

    public void setMapCoord(Coordinate mapCoord) {
        this.mapCoord = mapCoord;
    }

}
