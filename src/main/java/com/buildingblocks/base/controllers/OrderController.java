package com.buildingblocks.base.controllers;

import com.buildingblocks.base.entities.Order;
import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.OrderNotFoundException;
import com.buildingblocks.base.exceptions.UserNotFoundException;
import com.buildingblocks.base.repositories.OrderRepository;
import com.buildingblocks.base.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    /*@GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return userOptional.get().getOrders();
    }*/

    @GetMapping(value = "/{userId}/orders", produces = {"application/hal+json"})
    public CollectionModel<Order> getOrdersFromUser(@PathVariable Long userId) throws UserNotFoundException, OrderNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        List<Order> orders = userOptional.get().getOrders();

        for(final Order order : orders) {
            Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(userId, order.getOrderid())).withSelfRel();
            order.add(selfLink);
        }
        Link link = linkTo(methodOn(OrderController.class).getOrdersFromUser(userId)).withSelfRel();
        CollectionModel<Order> result = CollectionModel.of(orders, link);
        return result;
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Order getOrderById(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) throws UserNotFoundException, OrderNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }

        User user = userOptional.get();
        for (Order order: user.getOrders()) {
            if(order.getOrderid() == orderId) {
                return order;
            }
        }
        throw new OrderNotFoundException("Order not exists for the user");
    }

    @PostMapping("/{userId}/orders")
    public Order createOrder(@PathVariable("userId") Long userId, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if( !userOptional.isPresent()) {
            throw new UserNotFoundException("User not found in repository");
        }

        User user = userOptional.get();
        order.setUser(user);
        return orderRepository.save(order);
    }
}
