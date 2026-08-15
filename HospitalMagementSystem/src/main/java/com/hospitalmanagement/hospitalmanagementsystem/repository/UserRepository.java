package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    User findUserByEmail(String email);
}