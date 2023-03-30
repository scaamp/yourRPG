package com.example.REST_API.repositories;

import com.example.REST_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findById(long id);
}
