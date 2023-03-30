package com.example.REST_API.services;

import com.example.REST_API.entities.User;
import com.example.REST_API.dtos.UserRequest;
import com.example.REST_API.dtos.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    void addUser(UserRequest userRequest);

    User getUserById(Long id);

    void updateUser(Long id, User user);

    void deleteUser(Long id);
}
