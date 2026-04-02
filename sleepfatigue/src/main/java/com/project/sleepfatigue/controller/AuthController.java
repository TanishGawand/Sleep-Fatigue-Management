package com.project.sleepfatigue.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.sleepfatigue.model.User;
import com.project.sleepfatigue.service.AuthService;
import com.project.sleepfatigue.service.DataService;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private AuthService service;
    private DataService dataService;

@GetMapping("/test-data")
public Object testData() {
    return dataService.getRandomData();
}

    // ✅ REGISTER API
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return service.register(user);
    }

    // ✅ LOGIN API
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        boolean success = service.login(
                user.getUsername(),
                user.getPassword()
        );

        return success ? "Login successful" : "Invalid credentials";
    }
}
