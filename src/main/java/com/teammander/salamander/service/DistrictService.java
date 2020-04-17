package com.teammander.salamander.service;

import com.teammander.salamander.map.District;
import com.teammander.salamander.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {
    DistrictRepository dr;

    @Autowired
    public DistrictService(DistrictRepository dr) {
        this.dr = dr;
    }

    public DistrictRepository getDr() {
        return dr;
    }

    public District getDistrict(String canonName){
        return null;
    }

    public void updateAll(){
    }

}
