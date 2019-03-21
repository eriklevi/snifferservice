package com.example.snifferservice.repositories;

import com.example.snifferservice.entities.Building;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingsRepository extends MongoRepository<Building, String> {
    boolean existsByName(String name);
}
