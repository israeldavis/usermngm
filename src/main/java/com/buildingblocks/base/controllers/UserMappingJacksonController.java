package com.buildingblocks.base.controllers;

import com.buildingblocks.base.entities.User;
import com.buildingblocks.base.services.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/jacksonfilter/users")
public class UserMappingJacksonController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public MappingJacksonValue getUserById(@PathVariable @Min(1) Long id) {

        Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();

        Set<String> fields = new HashSet<>();
        fields.add("id");
        fields.add("username");
        fields.add("ssn");
        fields.add("orders");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        MappingJacksonValue mapper = new MappingJacksonValue(user);

        mapper.setFilters(filterProvider);

        return mapper;
    }

    @GetMapping("/params/{id}")
    public MappingJacksonValue getUserById2(@PathVariable @Min(1) Long id,
                                            @RequestParam Set<String> fields) {

        Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        MappingJacksonValue mapper = new MappingJacksonValue(user);

        mapper.setFilters(filterProvider);

        return mapper;
    }
}
