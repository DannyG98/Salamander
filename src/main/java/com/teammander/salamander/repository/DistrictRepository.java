package com.teammander.salamander.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teammander.salamander.map.District;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DistrictRepository {
    //EntityManager em;
    HashMap<String, District> allDistricts;

    @Autowired
    public DistrictRepository() {
        allDistricts = new HashMap<>();
    }

    public District findDistrict(String canonName) {
        return allDistricts.get(canonName);
    }

    public List<District> getAllDistricts() {
        return new ArrayList<>(allDistricts.values());
    }

    public void insertDistrict(District district) {
        allDistricts.put(district.getCanonName(), district);
    }
}
