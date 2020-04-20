package com.teammander.salamander.controller;

import java.util.ArrayList;
import java.util.List;

import com.teammander.salamander.map.District;
import com.teammander.salamander.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/district")
public class DistrictController {
    DistrictService ds;

    @Autowired
    public DistrictController(DistrictService ds) {
        this.ds = ds;
    }

    public DistrictService getDs() {
        return ds;
    }

    @PostMapping("/getMultipleDistricts")
    public List<District> getMultipleDistricts(@RequestBody List<String> query) {
        List<District> queryResponse = new ArrayList<>();
        for (String s : query) {
            queryResponse.add(getDs().getDistrict(s));
        }
        return queryResponse;
    } 

    @GetMapping("/getDistrict/{districtCanonName}")
    public District getDistrict(@PathVariable String districtCanonName) {
        return getDs().getDistrict(districtCanonName);
    }

    @PostMapping("/uploadDistrict")
    public void uploadDistrict(@RequestBody District district) {
        getDs().insertDistrict(district);
    }

    @PostMapping("/multiUploadDistricts")
    public void multiUploadDistricts(@RequestBody List<District> districts) {
        for (District s : districts)
            getDs().insertDistrict(s);
    }
}
