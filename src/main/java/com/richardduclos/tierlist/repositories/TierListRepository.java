package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.TierList;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TierListRepository extends JpaRepository<TierList, Integer> {
}
