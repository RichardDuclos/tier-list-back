package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.TierList;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.TierListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tier-lists")
@CrossOrigin(origins = "http://localhost:4200")
public class TierListController {

    @Autowired
    private TierListRepository tierListRepository;

    @GetMapping("")
    public @ResponseBody Iterable<TierList> getAll() {
        return tierListRepository.findAll();
    }
    @GetMapping("/{id}")
    public @ResponseBody TierList getOne(@PathVariable Integer id) {
        return  tierListRepository.findById(id)
                .orElseThrow(() -> new MvcEntityNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        Optional<TierList> tierList = tierListRepository.findById(id);
        tierList.ifPresent((TierList) -> {
            tierListRepository.delete(TierList);
        });
    }

    @PostMapping(path = "")
    public @ResponseBody TierList create(@RequestBody TierList tierList) {
        tierListRepository.save(tierList);
        return tierList;
    }

}
