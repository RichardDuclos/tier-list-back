package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.Element;
import com.richardduclos.tierlist.entities.Rank;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/elements")
public class ElementController {

    @Autowired
    private ElementRepository elementRepository;


    @PostMapping(path = "")
    public @ResponseBody Element create(@RequestBody Element element) {
        elementRepository.save(element);
        return element;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Element update(@RequestBody Element element, @PathVariable Integer id) {
        element.setId(id);
        elementRepository.save(element);
        return element;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Element getOne(@PathVariable Integer id) {
        return  elementRepository.findById(id)
                .orElseThrow(() -> new MvcEntityNotFoundException(id));
    }

    @GetMapping(path = "")
    public @ResponseBody Iterable<Element> getAll() {
        return elementRepository.findAll();
    }
}
