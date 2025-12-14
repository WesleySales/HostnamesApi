package com.wesley.ValidadorHostnames.services;

import com.wesley.ValidadorHostnames.entities.AllowedHostname;
import com.wesley.ValidadorHostnames.repositories.AllowedHostnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhitelistService {

    @Autowired
    private AllowedHostnameRepository repository;

    public boolean isAllowed(String hostname) {
//        System.out.println("Buscando no banco for ("+hostname+")");

        // Verifica se existe um host exatamente igual
        if (repository.findByHostname(hostname).isPresent()) {
            return true;
        }

        // Verifica se existe como willcard
        List<AllowedHostname> wildcards = repository.findByWildcardTrue();

        for (AllowedHostname allowed : wildcards) {
            if (hostname.endsWith("." + allowed.getHostname())) {
                return true;
            }
        }

        return false;
    }

    public List<AllowedHostname> listaDeHosts(){
        System.out.println("Acessando o banco de dados");
        return repository.findAll();
    }

    public Long countTotalAllowedHosts(){
        return repository.count();
    }

}
