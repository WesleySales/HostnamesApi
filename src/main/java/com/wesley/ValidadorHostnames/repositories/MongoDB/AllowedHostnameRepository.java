
package com.wesley.ValidadorHostnames.repositories.MongoDB;

import com.wesley.ValidadorHostnames.entities.MongoDB.AllowedHostname;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("allowedHostnameMongoRepository")
public interface AllowedHostnameRepository extends MongoRepository<AllowedHostname, String> {

//    List<AllowedHostname> findByHostname(String hostname);

    Optional<AllowedHostname> findByHostname(String hostname);

    List<AllowedHostname> findByHostnameIn(List<String> hostnames);

    long count();

    List<AllowedHostname> findAll();


}