package com.wesley.ValidadorHostnames.repositories.MongoDB;

import com.wesley.ValidadorHostnames.entities.MongoDB.HostnameSearched;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HostnameSearchedRepository extends MongoRepository<HostnameSearched, String> {

    List<HostnameSearched> findByCheckedAtBetweenOrderByCheckedAtDesc(LocalDateTime start, LocalDateTime end);
}