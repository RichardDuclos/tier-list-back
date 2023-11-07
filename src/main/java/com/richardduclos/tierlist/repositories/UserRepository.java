package com.richardduclos.tierlist.repositories;

import com.richardduclos.tierlist.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
