package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.Element;
import com.richardduclos.tierlist.entities.Rank;
import com.richardduclos.tierlist.entities.TierListState;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.ElementRepository;
import com.richardduclos.tierlist.repositories.RankRepository;
import com.richardduclos.tierlist.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;
import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1/elements")
public class ElementController {

    @Autowired
    ElementService elementService;

    @Autowired ElementRepository elementRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    DataSource dataSource;

    @PostMapping(path = "")
    public @ResponseBody Element create(@RequestBody Element element) throws  SQLException {
        Optional<Rank> dbRank = rankRepository.findById(element.getRank().getId());
        if(dbRank.isEmpty()) {
            return null;
        }
        Rank rank = dbRank.get();
        if(!rank.getTierlist().isDraft()) {
            return null;
        }

        element = elementService.addBlob(element);
        elementRepository.save(element);
        elementRepository.save(element);
        return element;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Element update(@RequestBody Element element, @PathVariable Integer id) throws SQLException {
        Optional<Rank> dbRank = rankRepository.findById(element.getId());
        if(dbRank.isEmpty()) {
            return null;
        }
        Rank rank = dbRank.get();
        if(!rank.getTierlist().isDraft()) {
            return null;
        }
        element.setId(id);
        Optional<Element> dbElement = elementRepository.findById(element.getId());
        if(dbElement.isEmpty()) {
            return null;
        }
        Element initialElement = dbElement.get();
        if(!Objects.equals(initialElement.getOrder(), element.getOrder())) {
            initialElement.getRank().getElements().forEach((Element oldElement) -> {
                if(Objects.equals(oldElement.getOrder(), element.getOrder())) {
                    Integer temp = oldElement.getOrder();
                    oldElement.setOrder(initialElement.getOrder());
                    initialElement.setOrder(temp);
                    elementRepository.save(oldElement);
                    elementRepository.save(initialElement);
                }
            });
        }
        elementRepository.save(element);
        return element;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Element getOne(@PathVariable Integer id) {
        return  elementRepository.findById(id)
                .orElseThrow(() -> new MvcEntityNotFoundException(id));
    }

    @GetMapping(path = "")
    public @ResponseBody Iterable<Element> getAll(
            @RequestParam("tag") String tag
    ) {
        if(!tag.isEmpty()) {
            List<Element> elements = elementRepository.findAll();
            List<Element> filteredElements = new ArrayList<>();
            for(Element element: elements) {
                if(Objects.equals(element.getTag(), tag)) {
                    if(element.getRank().getTierlist().getApprovedState().equals(TierListState.APROVED)) {
                        element.setRank(null);
                        element.setId(null);
                        filteredElements.add(element);
                    }
                }
            }

            return filteredElements;

        }
        return new ArrayList<>();
    }

}
