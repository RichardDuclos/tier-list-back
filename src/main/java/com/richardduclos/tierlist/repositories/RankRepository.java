package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.Rank;
import org.springframework.data.repository.CrudRepository;

public interface RankRepository extends CrudRepository<Rank, Integer> {
}
