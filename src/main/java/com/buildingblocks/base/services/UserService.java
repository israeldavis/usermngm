package com.buildingblocks.base.services;

import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.UserExistsException;
import com.buildingblocks.base.exceptions.UserNotFoundException;
import com.buildingblocks.base.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws UserExistsException {
        User userexists = userRepository.findUserByUsername(user.getUsername());
        if(userexists != null) {
            throw new UserExistsException("The username already has been taken.");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFoundException("User not found in repository");
        }

        return user;
    }

    public User updateUserById(Long id, User user) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found in repository, provide the correct user id.");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id)  {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User not found in repository, provide the correct user id.");
        }

        userRepository.deleteById(id);

    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
