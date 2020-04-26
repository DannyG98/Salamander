package com.teammander.salamander.service;

import java.util.List;
import java.util.Optional;

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
        return this.dr;
    }

    public District getDistrict(String canonName) {
        return getDr().findById(canonName).orElse(null);
    }

    public void insertDistrict(District district) {
        DistrictRepository dr = getDr();
        dr.saveAndFlush(district);
    }

    public void insertMultipleDistricts(List<District> districts) {
        DistrictRepository dr = getDr();
        dr.saveAll(districts);
        dr.flush();
    }
}
