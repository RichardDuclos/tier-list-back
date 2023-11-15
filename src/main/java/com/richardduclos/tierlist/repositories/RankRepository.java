package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Integer> {
    Optional<Rank> findByTierlistIdAndOrder(Integer tierListId, Integer order);
}
