package com.richardduclos.tierlist.controllers;

import com.richardduclos.tierlist.entities.Rank;
import com.richardduclos.tierlist.entities.TierList;
import com.richardduclos.tierlist.entities.User;
import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import com.richardduclos.tierlist.repositories.RankRepository;
import com.richardduclos.tierlist.repositories.TierListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1/ranks")
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private TierListRepository tierListRepository;

    @PostMapping(path = "")
    public @ResponseBody Rank create(@RequestBody Rank rank) {
        Optional<TierList> dbTierList = tierListRepository.findById(rank.getTierlist().getId());
        if(dbTierList.isPresent()) {
            TierList tierList = dbTierList.get();
            rank.setOrder(tierList.getRanks().size());
        }
        rankRepository.save(rank);
        return rank;
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Rank update(@RequestBody Rank rank, @PathVariable Integer id) {

        Optional<Rank> dbRank = rankRepository.findById(rank.getId());
        if(dbRank.isEmpty()) {
            return null;
        }
        Rank initialRank = dbRank.get();
        if (!Objects.equals(initialRank.getOrder(), rank.getOrder())) {
            initialRank.getTierlist().getRanks().forEach((Rank oldRank) -> {
                if(Objects.equals(oldRank.getOrder(), rank.getOrder())) {
                    Integer temp = oldRank.getOrder();
                    oldRank.setOrder(initialRank.getOrder());
                    initialRank.setOrder(temp);
                    rankRepository.save(oldRank);
                    rankRepository.save(initialRank);
                }
            });

        }
        return initialRank;
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
