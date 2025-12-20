package com.wesley.ValidadorHostnames.repositories.Postgre;

import com.wesley.ValidadorHostnames.entities.Postgre.AllowedHostname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("allowedHostnamePostgreRepository")
public interface AllowedHostnameRepository extends JpaRepository<AllowedHostname,Long> {

    @Query(value = """
        SELECT ah
        FROM AllowedHostname ah 
        WHERE :hostname LIKE CONCAT('%', ah.hostname)
    """)
    List<AllowedHostname> findByHostnameOtimizada(String hostname);

    Optional<AllowedHostname> findByHostname(String hostname);

    List<AllowedHostname> findByWildcardTrue();


}
