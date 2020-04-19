package com.teammander.salamander.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teammander.salamander.map.District;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DistrictRepository extends JpaRepository<District, String>{
    //EntityManager em;
    // HashMap<String, District> allDistricts;

    // @Autowired
    // public DistrictRepository() {
    //     allDistricts = new HashMap<>();
    // }

    // public District getDistrict (String canonName) {
    //     return allDistricts.get(canonName);
    // }

    // public void updateChanges() {

    // }

    // public void insertNewDistrict(District district) {
    //     allDistricts.put(district.getCanonName(), district);
    // }
}
