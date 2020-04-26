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
        return dr;
    }

    public District getDistrict(String canonName) {
        return getDr().findById(canonName).orElse(null);
    }

    public void insertDistrict(District district) {
        getDr().save(district);
        getDr().flush();
    }

    public void insertMultipleDistricts(List<District> districts) {
        getDr().saveAll(districts);
        getDr().flush();
    }
}
