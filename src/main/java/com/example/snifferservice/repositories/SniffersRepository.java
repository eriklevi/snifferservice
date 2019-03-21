package com.example.snifferservice.repositories;

import com.example.snifferservice.entities.Sniffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SniffersRepository extends MongoRepository<Sniffer,String> {
    Boolean existsByMac(String mac);
    List<Sniffer> findByBuilding(String building);
    List<Sniffer> findByRoom(String room);
    Optional<Sniffer> findByMacID(String macID);
}
