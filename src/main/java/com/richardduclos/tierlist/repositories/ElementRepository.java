package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, Integer> {

}
