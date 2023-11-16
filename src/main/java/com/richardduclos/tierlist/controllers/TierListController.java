package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.*;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.TierListRepository;
import com.richardduclos.tierlist.repositories.UserRepository;
import com.richardduclos.tierlist.services.ElementService;
import com.richardduclos.tierlist.services.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.*;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/tier-lists")
@CrossOrigin(origins = "http://localhost:4200")
public class TierListController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private TierListRepository tierListRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElementService elementService;

    @GetMapping("")
    public @ResponseBody Iterable<TierList> getAll(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;
        List<TierList> tierLists = tierListRepository.findAll();
        List<TierList> filteredList = new ArrayList<>();
        for (TierList tierList: tierLists) {
            if(tierList.getApprovedState() == TierListState.APROVED
                || (user.getId().equals(tierList.getOwner().getId()))
            ) {
                filteredList.add(tierList);
            }
        }
        return filteredList;
    }
    @GetMapping("/{id}")
    public @ResponseBody TierList getOne(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails
                                         ) {
        User user = (User) userDetails;
        var e = tierListRepository.findById(id);
        if(e.isEmpty()) {
            throw new MvcEntityNotFoundException(id);
        }
        TierList tierList = e.get();
        if((tierList.getApprovedState() == TierListState.PENDING) || (tierList.getApprovedState() == TierListState.REFUSED) ) {
            if((!tierList.getOwner().getId().equals(user.getId())) && user.getRole() != Role.ADMIN) {
                return null;
            }
        }
        for(Rank rank: tierList.getRanks()) {
//            Set<Element> elements = new HashSet<>();
            for(Element element: rank.getElements()) {
                try {
                    element = this.elementService.retrieveBlob(element);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
//            rank.setElements(elements);
        }

        return  tierList;
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
    public @ResponseBody TierList create(
            @Validated(TierList.Creation.class) @RequestBody TierList tierList,
            @AuthenticationPrincipal UserDetails userDetails

    ) {
        User user = (User) userDetails;
        tierList.setOwner(user);
        tierListRepository.save(tierList);
        return tierList;
    }
//    @PutMapping(path = "/{id}")
//    public @ResponseBody TierList update(
//            @RequestBody TierList tierList,
//            @PathVariable Integer id,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        User user = (User) userDetails;
//
//        Optional<TierList> dbTierList = this.tierListRepository.findById(id);
//        if(dbTierList.isEmpty()) {
//            return null;
//        }
//        TierList initialTierList = dbTierList.get();
//        if(!initialTierList.isDraft()) {
//            return null;
//        }
//        var owner = initialTierList.getOwner();
//        if(!initialTierList.getOwner().getId().equals(user.getId())) {
//            return null;
//        }
//        if(initialTierList.isDraft() && !tierList.isDraft()) {
//            initialTierList.setDraft(false);
//        }
//        tierListRepository.save(initialTierList);
//        return tierList;
//    }



}
