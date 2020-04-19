package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import mil.nga.sf.geojson.Position;

//enum for type of errors
enum ErrorType{
    DAT_ELECT_MISMATCH, DAT_DEMO_MISMATCH, PRCT_GAP, PRCT_OVERLAP, PRCT_OPEN, PRCT_ENCLOSE;
}

@Entity
@Table(name = "data_errors")
@EntityListeners(AuditingEntityListener.class)
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

    @Id
    @GeneratedValue
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Enumerated(EnumType.STRING)
    public ErrorType getEType() {
        return eType;
    }

    public void setEType(ErrorType eType) {
        this.eType = eType;
    }

    @ManyToMany
    @JoinTable(name = "error_precinct_map", joinColumns = @JoinColumn(name = "eid"),
                inverseJoinColumns = @JoinColumn(name = "canonName"))
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
