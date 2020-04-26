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

    @Id
    @Column(name = "CANON_NAME")
    public String getCanonName() {
        return this.canonName;
    }

    public void setCanonName(String canonName) {
        this.canonName = canonName;
    }

    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return this.displayName;
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
        return this.demoData;
    }

    public void setDemoData(DemographicData demoData) {
        this.demoData = demoData;
    }

    @OneToOne
    public ElectionData getElecData() {
        return this.elecData;
    }

    public void setElecData(ElectionData elecData) {
        this.elecData = elecData;
    }
}
