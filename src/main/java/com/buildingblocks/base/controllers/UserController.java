package com.buildingblocks.base.controllers;

import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.UserExistsException;
import com.buildingblocks.base.exceptions.UserNotFoundException;
import com.buildingblocks.base.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> crateUser(@RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        try {
            return userService.getUserById(id).get();
        } catch(UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch(UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }
}
