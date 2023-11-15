package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.dto.TierListAproving;
import com.richardduclos.tierlist.entities.Role;
import com.richardduclos.tierlist.entities.TierList;
import com.richardduclos.tierlist.entities.TierListState;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.repositories.TierListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    @Autowired
    private TierListRepository tierListRepository;

    @GetMapping("/tier-lists/pending")
    public @ResponseBody Iterable<TierList> getAllPendingTierList() {
        List<TierList> tierLists = tierListRepository.findAll();
        List<TierList> filteredList = new ArrayList<>();
        for (TierList tierList: tierLists) {
            if(tierList.getApprovedState() == TierListState.PENDING && !tierList.isDraft()) {
                filteredList.add(tierList);
            }
        }
        return filteredList;
    }

    @PostMapping("/tier-lists/{id}")
    public @ResponseBody TierList approveTierList(
            @RequestBody TierListAproving approving,
            @PathVariable Integer id
            ) {
        Optional<TierList> dbTierList= tierListRepository.findById(id);
        if(dbTierList.isEmpty()) {
            return null;
        }
        TierList tierList = dbTierList.get();
        if(approving.getState()) {
            tierList.setApprovedState(TierListState.APROVED);
        } else {
            tierList.setApprovedState(TierListState.REFUSED);
        }
        tierListRepository.save(tierList);
        return tierList;
    }

    @GetMapping("/me")
    public User me (
            @AuthenticationPrincipal UserDetails userDetails

    ) {
        return (User) userDetails;
    }
}
