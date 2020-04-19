package com.teammander.salamander.map;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import mil.nga.sf.geojson.Geometry;


public abstract class Region {
    String canonName;
    String displayName;
    Geometry geometry;
    DemographicData demoData;
    Election elecData;

    public Region(String canonName, String displayName, Geometry geometry, DemographicData demoData, Election elecData) {
        this.canonName = canonName;
        this.displayName = displayName;
        this.geometry = geometry;
        this.demoData = demoData;
        this.elecData = elecData;
    }

    public String getCanonName() {
        return canonName;
    }

    public void setCanonName(String canonName) {
        this.canonName = canonName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geoemtry) {
        this.geometry = geoemtry;
    }

    public DemographicData getDemoData() {
        return demoData;
    }

    public void setDemoData(DemographicData demoData) {
        this.demoData = demoData;
    }

    public Election getElecData() {
        return elecData;
    }

    public void setElecData(Election elecData) {
        this.elecData = elecData;
    }
}
