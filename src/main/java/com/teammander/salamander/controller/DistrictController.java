package com.teammander.salamander.controller;

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

    @GetMapping("/getDistrict/{districtCanonName}")
    public District getDistrict(@PathVariable String districtCanonName) {
        return getDs().getDistrict(districtCanonName);
    }

    @PostMapping("/uplaodDistrict")
    public void uploadDistrict(@RequestBody District district) {
        getDs().insertDistrict(district);
    }
}
