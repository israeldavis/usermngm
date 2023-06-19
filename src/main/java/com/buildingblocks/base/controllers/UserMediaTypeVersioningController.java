package com.buildingblocks.base.controllers;

import com.buildingblocks.base.dtos.UserDTOV1;
import com.buildingblocks.base.dtos.UserDTOV2;
import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.exceptions.UserNotFoundException;
import com.buildingblocks.base.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/versioning/mediatype/users")
public class UserMediaTypeVersioningController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = "application/vnd.stacksimplify.app-v1+json")
    public UserDTOV1 getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        UserDTOV1 userDTOV1 = modelMapper.map(user, UserDTOV1.class);
        return userDTOV1;
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.stacksimplify.app-v2+json")
    public UserDTOV2 getUserByIdv2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        UserDTOV2 userDTOV2 = modelMapper.map(user, UserDTOV2.class);
        return userDTOV2;
    }

}
