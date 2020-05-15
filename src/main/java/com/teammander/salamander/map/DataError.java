package com.teammander.salamander.map;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name = "DATA_ERRORS")
public class DataError {

    int eid;
    ErrorType eType;
    boolean resolved;
    Set<String> affectedPrct;

    public DataError(int eid, ErrorType eType, Set<String> affectedPrct) {
        this.eid = eid;
        this.eType = eType;
        this.affectedPrct = affectedPrct;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Column(name = "resolved_status")
    public boolean getResolved() {
        return this.resolved;
    }

    public void setResolved(boolean newStatus) {
        this.resolved = newStatus;
    }

    @Enumerated(EnumType.STRING)
    public ErrorType getEType() {
        return eType;
    }

    public void setEType(ErrorType eType) {
        this.eType = eType;
    }

    @Lob
    @Column(name = "affected_prcts")
    public Set<String> getAffectedPrct() {
        return affectedPrct;
    }

    public void setAffectedPrct(Set<String> affectedPrct) {
        this.affectedPrct = affectedPrct;
    }
}
