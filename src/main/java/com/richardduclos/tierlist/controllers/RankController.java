package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.Rank;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/ranks")
public class RankController {

    @Autowired
    private RankRepository rankRepository;


    @PostMapping(path = "")
    public @ResponseBody Rank create(@RequestBody Rank rank) {
        rankRepository.save(rank);
        return rank;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Rank update(@RequestBody Rank rank, @PathVariable Integer id) {
        rank.setId(id);
        rankRepository.save(rank);
        return rank;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Rank getOne(@PathVariable Integer id) {
        return  rankRepository.findById(id)
                .orElseThrow(() -> new MvcEntityNotFoundException(id));
    }

    @GetMapping(path = "")
    public @ResponseBody Iterable<Rank> getAll() {
        return rankRepository.findAll();
    }
}
