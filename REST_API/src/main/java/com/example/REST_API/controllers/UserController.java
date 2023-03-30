package com.example.REST_API.controllers;

import com.example.REST_API.entities.User;
import com.example.REST_API.dtos.UserRequest;
import com.example.REST_API.dtos.UserResponse;
import com.example.REST_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) throws URISyntaxException {
        userService.addUser(userRequest);
        ResponseEntity.created(new URI("/users/" + userRequest.getUserId())).body(userRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PutMapping({"/{id}"})
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String title) {
//        try {
//            List<User> users = new ArrayList<User>();
//
//            if (title == null)
//                userRepository.findAll().forEach(users::add);
//            else
//                userRepository.findById(title).forEach(users::add);
//
//            if (users.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
//        Optional<User> userData = userRepository.findById(id);
//
//        if (userData.isPresent()) {
//            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/user")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        try {
//            User _user = userRepository
//                    .save(new User(user.getId()));
//            return new ResponseEntity<>(_user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
