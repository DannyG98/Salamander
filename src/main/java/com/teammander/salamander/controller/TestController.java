package com.teammander.salamander.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;


@Controller
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("/testPoint")
    public ResponseEntity<String> testRecv(@RequestBody String payload)
     throws IOException {
        System.out.println(payload);
        String responseStr = "response from testRecv";
        return new ResponseEntity<>(responseStr, new HttpHeaders(), HttpStatus.OK);
    }
}