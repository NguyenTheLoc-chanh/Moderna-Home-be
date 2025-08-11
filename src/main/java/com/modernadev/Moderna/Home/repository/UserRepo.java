package com.modernadev.Moderna.Home.repository;

import com.modernadev.Moderna.Home.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
