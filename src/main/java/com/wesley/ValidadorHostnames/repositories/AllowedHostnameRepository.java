package com.wesley.ValidadorHostnames.repositories;

import com.wesley.ValidadorHostnames.entities.AllowedHostname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllowedHostnameRepository extends JpaRepository<AllowedHostname,Long> {

    Optional<AllowedHostname> findByHostname(String hostname);


    List<AllowedHostname> findByWildcardTrue();


}
