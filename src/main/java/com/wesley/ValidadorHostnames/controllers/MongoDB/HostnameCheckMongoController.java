package com.wesley.ValidadorHostnames.controllers.MongoDB;

import com.wesley.ValidadorHostnames.entities.MongoDB.HostnameSearched;
import com.wesley.ValidadorHostnames.entities.dtos.CheckResponseDTO;
import com.wesley.ValidadorHostnames.services.MongoDB.HostnameCheckService;
import com.wesley.ValidadorHostnames.services.dtos.HistoryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("hostnameCheckMongoController")
@RequestMapping("/api/v1/check")
@CrossOrigin(origins = "http://localhost:5173")
public class HostnameCheckMongoController {

    @Autowired
    private HostnameCheckService checkService;

    @PostMapping
    public ResponseEntity<List<CheckResponseDTO>> validateHostnames(@RequestBody List<String> hostnames) {
        List<CheckResponseDTO> results = hostnames.stream()
                .map(hostname -> checkService.check(hostname))
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/history")
    public ResponseEntity<List<HostnameSearched>> getAllHistory() {
        List<HostnameSearched> history = checkService.exibirHistorico();
        return ResponseEntity.ok(history);
    }

    @PostMapping("/history/range")
    public ResponseEntity<List<HostnameSearched>> getHistoryByRange(@RequestBody HistoryRequestDTO data) {
        return ResponseEntity.ok(checkService.buscarHistoricoPorIntervalo(data));
    }

}