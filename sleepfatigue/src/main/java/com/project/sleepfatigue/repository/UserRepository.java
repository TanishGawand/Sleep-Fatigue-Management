package com.project.sleepfatigue.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.sleepfatigue.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
    

