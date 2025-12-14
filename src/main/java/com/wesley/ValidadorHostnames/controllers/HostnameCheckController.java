package com.wesley.ValidadorHostnames.controllers;

import com.wesley.ValidadorHostnames.entities.AllowedHostname;
import com.wesley.ValidadorHostnames.entities.dtos.CheckRequestDTO;
import com.wesley.ValidadorHostnames.entities.dtos.CheckResponseDTO;
import com.wesley.ValidadorHostnames.services.HostnameCheckService;
import com.wesley.ValidadorHostnames.services.WhitelistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HostnameCheckController {

    @Autowired
    private HostnameCheckService hostnameCheckService;
    @Autowired
    private WhitelistService whitelistService;

    @PostMapping("/check")
    public ResponseEntity<List<CheckResponseDTO>> check(@Valid @RequestBody CheckRequestDTO request) {
        List<CheckResponseDTO> result = new ArrayList<>();

        for (String hostname : request.hostnames()) {
            result.add(hostnameCheckService.check(hostname));
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<AllowedHostname>> findAll(){
        List<AllowedHostname> result = whitelistService.listaDeHosts();
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/cache")
    public ResponseEntity<?> findAllCache(){
        List<CheckResponseDTO> result = hostnameCheckService.getResultsCache();

        if(result.isEmpty()){
            return ResponseEntity.ok().body("O cache está vazio!");
        }

        return ResponseEntity.ok().body(result);
    }

}

