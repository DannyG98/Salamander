package com.teammander.salamander.controller;

import java.util.ArrayList;
import java.util.List;

import com.teammander.salamander.map.District;
import com.teammander.salamander.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return this.ds;
    }

    @PostMapping("/getMultipleDistricts")
    public List<District> getMultipleDistricts(@RequestBody List<String> query) {
        DistrictService ds = getDs();
        List<District> queryResponse = new ArrayList<>();
        for (String s : query) {
            District d = ds.getDistrict(s);
            queryResponse.add(d);
        }
        return queryResponse;
    } 

    @GetMapping("/getDistrict/{districtCanonName}")
    public District getDistrict(@PathVariable String districtCanonName) {
        DistrictService ds = getDs();
        District foundDistrict = ds.getDistrict(districtCanonName);
        return foundDistrict;
    }

    /* ONLY FOR DEV USE REMOVE FOR FINAL BUILD **/

    @PostMapping("/uploadDistrict")
    public void uploadDistrict(@RequestBody District district) {
        DistrictService ds = getDs();
        ds.insertDistrict(district);
    }

    @PostMapping("/multiUploadDistricts")
    public void multiUploadDistricts(@RequestBody List<District> districts) {
        DistrictService ds = getDs();
        ds.insertMultipleDistricts(districts);
    }
}
