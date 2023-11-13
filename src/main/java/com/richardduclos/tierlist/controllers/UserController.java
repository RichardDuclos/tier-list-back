package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @PutMapping(path = "/{id}")
    public @ResponseBody User update(@RequestBody User user, @PathVariable UUID id) {
        user.setId(id);
        userRepository.save(user);
        return user;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User getOne(@PathVariable UUID id) {
        return  userRepository.findById(id)
                .orElseThrow(() -> new MvcEntityNotFoundException(id));
    }

    @GetMapping(path = "")
    public @ResponseBody Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
