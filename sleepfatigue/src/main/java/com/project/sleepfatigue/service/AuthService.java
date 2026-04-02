package com.project.sleepfatigue.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.sleepfatigue.model.User;
import com.project.sleepfatigue.repository.UserRepository;

@Service

public class AuthService {
     @Autowired
    private UserRepository repo;

    public String register(User user) {

        // Check if user exists
        if (repo.findByUsername(user.getUsername()) != null) {
            return "User already exists";
        }

        repo.save(user);
        return "User registered successfully";
    }
     public boolean login(String username, String password) {

        User user = repo.findByUsername(username);

        return user != null && user.getPassword().equals(password);
    }
}
