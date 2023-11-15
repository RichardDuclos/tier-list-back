package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.Element;
import com.richardduclos.tierlist.entities.Rank;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.ElementRepository;
import com.richardduclos.tierlist.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1/elements")
public class ElementController {

    @Autowired
    ElementService elementService;

    @Autowired ElementRepository elementRepository;

    @Autowired
    DataSource dataSource;

    @PostMapping(path = "")
    public @ResponseBody Element create(@RequestBody Element element) throws  SQLException {
        element = elementService.addBlob(element);
        elementRepository.save(element);
        elementRepository.save(element);
        return element;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Element update(@RequestBody Element element, @PathVariable Integer id) throws SQLException {
        element.setId(id);
        element = elementService.addBlob(element);
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
