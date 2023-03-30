package com.example.REST_API.services;

import com.example.REST_API.entities.User;
import com.example.REST_API.dtos.UserRequest;
import com.example.REST_API.dtos.UserResponse;
import com.example.REST_API.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserResponse> getAllUsers()
    {
        /*return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(entity -> new UserResponse(entity.getUser_id(), entity.getName(), entity.getAge(), entity.getMovie_id().getMovie_id()))
                .collect(Collectors.toList());*/
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>(users.size());

        for (User u : users) {
            UserResponse userResponse = new UserResponse(u.getUserId(), u.getName(), u.getStrength(), u.getSpellbooks());
            userResponses.add(userResponse);
        }

        LOG.info("getAll - number of users: " + userResponses.size());
        return userResponses;
    }

    @Override
    public void addUser(UserRequest userRequest)
    {
        User user = new User();
        user.setUserId(userRequest.getUserId());
        user.setName(userRequest.getName());
        user.setStrength(userRequest.getStrength());
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(Long id, User userArg) {
        User user = userRepository.findById(id).get();
        System.out.println(userArg.toString());
        user.setName(userArg.getName());
        user.setStrength(userArg.getStrength());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
