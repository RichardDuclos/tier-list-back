package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.TierList;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TierListRepository extends CrudRepository<TierList, Integer> {
}
