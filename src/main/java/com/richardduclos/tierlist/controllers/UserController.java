package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "")
    public @ResponseBody User create(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody User update(@RequestBody User user, @PathVariable UUID id) {
        if (id != user.getId()) {
        }
        userRepository.save(user);
        return user;
    }
}
