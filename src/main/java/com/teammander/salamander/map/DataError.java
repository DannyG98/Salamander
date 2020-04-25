package com.teammander.salamander.map;

import java.util.Set;

import mil.nga.sf.geojson.Position;

public class DataError {

    int eid;
    ErrorType eType;
    Set<Precinct> affectedPrct;
    Position mapCoord;

    public DataError(int eid, ErrorType eType, Set<Precinct> affectedPrct, Position mapCoord) {
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

    public Position getMapCoord() {
        return mapCoord;
    }

    public void setMapCoord(Position mapCoord) {
        this.mapCoord = mapCoord;
    }

}
