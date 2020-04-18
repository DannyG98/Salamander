package com.teammander.salamander.map;
import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;

import java.util.List;

public abstract class Region {
    String canonName;
    String displayName;
    List<Polygon> shape;
    DemographicData demoData;
    Election elecData;

    public Region(String canonName, String displayName, List<Polygon> shape, DemographicData demoData, Election elecData) {
        this.canonName = canonName;
        this.displayName = displayName;
        this.shape = shape;
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

    public List<Polygon> getShape() {
        return this.shape;
    }

    public void setShape(List<Polygon> shape) {
        this.shape = shape;
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
