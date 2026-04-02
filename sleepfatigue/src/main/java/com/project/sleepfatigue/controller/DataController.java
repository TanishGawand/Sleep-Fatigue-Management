package com.project.sleepfatigue.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.sleepfatigue.service.DataService;
@RestController
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping("/test-data")
    public Object getData() {
        return dataService.getRandomData();
    }
}
