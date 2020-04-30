package com.teammander.salamander.map;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity(name = "STATES")
@EntityListeners(AuditingEntityListener.class)
public class State extends Region {

    Set<String> districtCNames;

    @ElementCollection
    @Column(name = "child_district")
    public Set<String> getDistrictCNames() {
        return this.districtCNames;
    }

    public void setDistrictCNames(Set<String> district) {
        this.districtCNames = district;
    }
}
