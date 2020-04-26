package com.teammander.salamander.map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.ElectionData;

import mil.nga.sf.geojson.Geometry;

@MappedSuperclass
public abstract class Region {
    String canonName;
    String displayName;
    Geometry geometry;
    DemographicData demoData;
    ElectionData elecData;

    // public Region(String canonName, String displayName, Geometry geometry, DemographicData demoData, ElectionData elecData) {
    //     this.canonName = canonName;
    //     this.displayName = displayName;
    //     this.geometry = geometry;
    //     this.demoData = demoData;
    //     this.elecData = elecData;
    // }

    @Id
    @Column(name = "CANON_NAME")
    public String getCanonName() {
        return canonName;
    }

    public void setCanonName(String canonName) {
        this.canonName = canonName;
    }

    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Lob
    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geoemtry) {
        this.geometry = geoemtry;
    }

    @OneToOne
    public DemographicData getDemoData() {
        return demoData;
    }

    public void setDemoData(DemographicData demoData) {
        this.demoData = demoData;
    }

    @OneToOne
    public ElectionData getElecData() {
        return elecData;
    }

    public void setElecData(ElectionData elecData) {
        this.elecData = elecData;
    }
}
