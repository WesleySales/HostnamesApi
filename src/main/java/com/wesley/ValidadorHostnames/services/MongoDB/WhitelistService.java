package com.wesley.ValidadorHostnames.services.MongoDB;

import com.wesley.ValidadorHostnames.entities.MongoDB.AllowedHostname;
import com.wesley.ValidadorHostnames.repositories.MongoDB.AllowedHostnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("whitelistMongoService")
public class WhitelistService {

    @Autowired
    private AllowedHostnameRepository repository;

    public AllowedHostname findAllowed(String hostname) {
        Optional<AllowedHostname> match = repository.findByHostname(hostname);

        if (match.isPresent()) return match.get();

        String host = hostname.substring(hostname.indexOf(".") + 1);

        System.out.println(host);

        Optional<AllowedHostname> wildcard = repository.findByHostname(host);

//        System.out.println(wildcard);
        if (wildcard.isPresent()) return wildcard.get();

        return null;
    }

    public Long countTotalAllowedHosts() {
        return repository.count();
    }
}