package com.buildingblocks.base.controllers;

import com.buildingblocks.base.dtos.UserMmDto;
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
@RequestMapping("/modelmapper/users")
public class UserModelMapperController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public UserMmDto getUserById(@PathVariable @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();

        UserMmDto userMmDto = modelMapper.map(user, UserMmDto.class);
        return userMmDto;
    }
}
