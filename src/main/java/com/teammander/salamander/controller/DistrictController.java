package com.teammander.salamander.controller;

import com.teammander.salamander.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity getDistrict(String canonName){
        return null;
    }
}
