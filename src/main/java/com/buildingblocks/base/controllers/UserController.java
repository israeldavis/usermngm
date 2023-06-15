package com.buildingblocks.base.controllers;

import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.OrderNotFoundException;
import com.buildingblocks.base.exceptions.UserNameNotFoundException;
import com.buildingblocks.base.exceptions.UserNotFoundException;
import com.buildingblocks.base.repositories.OrderRepository;
import com.buildingblocks.base.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }*/

    @GetMapping(produces = {"application/hal+json"})
    public CollectionModel<User> getAllUsers() throws OrderNotFoundException, UserNotFoundException {
        List<User> allUsers = userService.getAllUsers();

        for(User user : allUsers) {
            String userId = user.getId() + "";
            Link selfLink = linkTo(UserController.class).slash(userId).withSelfRel();
            user.add(selfLink);
            if(user.getOrders().size() > 0) {
                Link ordersLink = linkTo(methodOn(OrderController.class).getOrdersFromUser(user.getId())).withRel("allOrders");
                user.add(ordersLink);
            }
        }
        Link link = linkTo(UserController.class).withSelfRel();
        CollectionModel<User> result = CollectionModel.of(allUsers, link);
        return result;
    }

    @PostMapping
    public User crateUser(@Valid @RequestBody User user) {
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
