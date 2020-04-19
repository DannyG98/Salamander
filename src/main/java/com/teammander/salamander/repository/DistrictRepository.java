package com.teammander.salamander.repository;

import com.teammander.salamander.map.District;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DistrictRepository {
    //EntityManager em;

    @Autowired
    public DistrictRepository() {
        
    }

    public District getDistrict (String canonName){
        return null;
    }

    public void updateChanges(){

    }
}
