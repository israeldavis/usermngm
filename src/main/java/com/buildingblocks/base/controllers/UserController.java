package com.buildingblocks.base.controllers;

import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.UserNameNotFoundException;
import com.buildingblocks.base.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Api(tags = "User Managements RESTful Services", value = "User Controller", description = "Controller for User Management Service")
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Retrieve list of users")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Create a new user")
    @PostMapping
    public User crateUser(@ApiParam("User information for a new user to be created.") @Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        return userService.getUserById(id).get();
    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
        User user = userService.getUserByUsername(username);
        if(user == null) {
            throw new UserNameNotFoundException("Username '" + username + "' not found in User repository.");
        }
        return user;
    }
}
