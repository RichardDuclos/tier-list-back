package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer> {
}
