package com.wesley.ValidadorHostnames.services.MongoDB;

import com.wesley.ValidadorHostnames.entities.CacheEntry;
import com.wesley.ValidadorHostnames.entities.MongoDB.AllowedHostname;
import com.wesley.ValidadorHostnames.entities.MongoDB.HostnameSearched;
import com.wesley.ValidadorHostnames.entities.dtos.CheckResponseDTO;
import com.wesley.ValidadorHostnames.entities.enums.StatusHosts;
import com.wesley.ValidadorHostnames.repositories.MongoDB.AllowedHostnameRepository;
import com.wesley.ValidadorHostnames.repositories.MongoDB.HostnameSearchedRepository;
import com.wesley.ValidadorHostnames.services.ProbeService;
import com.wesley.ValidadorHostnames.services.dtos.HistoryRequestDTO;
import com.wesley.ValidadorHostnames.services.dtos.ProbeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("hostnameCheckMongoService")
public class HostnameCheckService {

    private final Map<String, CacheEntry> hostnameCache;
    private final long cacheTtlSeconds;

    @Autowired
    private WhitelistService whitelistService;

    @Autowired
    private ProbeService probeService;

    @Autowired
    private AllowedHostnameRepository allowedHostnameRepository;

    @Autowired
    private HostnameSearchedRepository hostnameSearchedRepository;

    @Autowired
    public HostnameCheckService(Map<String, CacheEntry> hostnameCache, long cacheTtlSeconds) {
        this.hostnameCache = hostnameCache;
        this.cacheTtlSeconds = cacheTtlSeconds;
    }

//    public CheckResponseDTO check(String hostname) {
//
//        hostname = hostname.toLowerCase();
//
//        CheckResponseDTO cachedResult = getFromCache(hostname);
//
//        if (cachedResult != null) {
//            return new CheckResponseDTO(
//                    cachedResult.id(),
//                    cachedResult.hostname(),
//                    cachedResult.status(),
//                    cachedResult.description()+"(CACHE)",
//                    cachedResult.ips(),
//                    cachedResult.rtt()
//            );
//        }
//
//        CheckResponseDTO finalResult;
//
//        var host = whitelistService.findAllowed(hostname);
//
//        if (host == null) {
//            finalResult = new CheckResponseDTO(
//                    hostname,
//                    hostname,
//                    StatusHosts.UNKNOWN,
//                    "Hostname não está na whitelist",
//                    null,
//                    null
//            );
//        } else {
//            try {
//                ProbeResult probe = probeService.probe(hostname);
//                finalResult = new CheckResponseDTO(
//                        host.getId(),
//                        hostname,
//                        StatusHosts.VALID,
//                        "Hostname encontrado e válido",
//                        probe.ips(),
//                        probe.rtt()
//                );
//            } catch (Exception e) {
//                finalResult = new CheckResponseDTO(
//                        host.getId(),
//                        hostname,
//                        StatusHosts.DISALLOWED,
//                        "Não passou pela probe.",
//                        null,
//                        null
//                );
//            }
//        }
//
//        try {
//            Double latencia = (finalResult.rtt() != null) ? finalResult.rtt() : 0.0;
//
//            saveHistory(host.getId(),hostname,finalResult.status().toString(),latencia);
//
//        } catch (Exception e) {
//            // Logamos o erro mas não travamos a resposta do usuário
//            System.err.println("Erro ao salvar histórico no MongoDB: " + e.getMessage());
//        }
//
//        putInCache(hostname, finalResult);
//        return finalResult;
//    }

   public CheckResponseDTO check(String hostname) {
        hostname = hostname.toLowerCase();

        // 1. No Cache, use o hostname original (para ser rápido)
        CheckResponseDTO cachedResult = getFromCache(hostname);

       if (cachedResult != null) {
            return new CheckResponseDTO(
                    cachedResult.id(),
                    cachedResult.hostname(),
                    cachedResult.status(),
                    cachedResult.description()+"(CACHE)",
                    cachedResult.ips(),
                    cachedResult.rtt()
            );
        }

        // 2. Busca o registro que concede a permissão (o "Pai" ou "Exato")
        var allowedRecord = whitelistService.findAllowed(hostname);

        if (allowedRecord == null) {
            return saveNotFound(hostname);
        }

        try {
            ProbeResult probe = probeService.probe(hostname);

            CheckResponseDTO finalResult = new CheckResponseDTO(
                    allowedRecord.getId(),       // Sempre o ID do registro raiz (ex: ID do amazon.com)
                    hostname,                    // O que o usuário digitou
                    StatusHosts.VALID,
                    "Permitido via: " + allowedRecord.getHostname(), // Informativo
                    probe.ips(),
                    probe.rtt()
            );

            saveHistory(allowedRecord.getId(), hostname, "VALID", (double) probe.rtt());
            putInCache(hostname, finalResult);
            return finalResult;

        } catch (Exception e) {
            return handleProbeError(allowedRecord.getId(), hostname);
        }
    }

    private CheckResponseDTO saveNotFound(String hostname) {
        CheckResponseDTO result = new CheckResponseDTO(
                null,
                hostname,
                StatusHosts.UNKNOWN,
                "Hostname não está na whitelist",
                null,
                null
        );

        saveHistory(null, hostname, StatusHosts.UNKNOWN.toString(), 0.0);

        return result;
    }

    private CheckResponseDTO getFromCache(String hostname) {
        CacheEntry entry = hostnameCache.get(hostname);

        if (entry != null) {

            if (entry.getCacheEntryTime().plusSeconds(this.cacheTtlSeconds).isBefore(Instant.now())) {
                System.out.println("Cache expirado (TTL): " + hostname);
                hostnameCache.remove(hostname);
                return null;
            }

            entry.setCacheEntryTime(Instant.now());
            return entry.getData();
        }
        return null;
    }

    private void putInCache(String hostname, CheckResponseDTO result) {
        // Apenas hosts encontrados no banco serão guardados no cache
        if (!result.status().equals(StatusHosts.UNKNOWN)) {
            CacheEntry entry = new CacheEntry(result);
            hostnameCache.put(hostname, entry);
        }
    }

    public List<CheckResponseDTO> getResultsCache(){

        List<CacheEntry> entries = new ArrayList<>(hostnameCache.values());

        List<CheckResponseDTO> results = new ArrayList<>(entries.size());

        for (CacheEntry entry : entries) {
            results.add(entry.getData());
        }

        return results;
    }

    public void saveHistory(String id, String hostname, String status, Double latencia) {

        HostnameSearched history = new HostnameSearched();

        history.setId(id);
        history.setHostnameId(hostname);
        history.setCheckedAt(LocalDateTime.now());
        history.setStatus(status);
        history.setLatencia(latencia);

        hostnameSearchedRepository.save(history);
    }

    public List<HostnameSearched> exibirHistorico(){
        return hostnameSearchedRepository.findAll();
    }

    public List<HostnameSearched> buscarHistoricoPorIntervalo(HistoryRequestDTO data) {
        if (data.dataInicio().isAfter(data.dataFim())) {
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
        }

        LocalDateTime start = data.dataInicio().atStartOfDay();
        LocalDateTime end = data.dataFim().atTime(LocalTime.MAX);

        return hostnameSearchedRepository.findByCheckedAtBetweenOrderByCheckedAtDesc(start, end);
    }

    private CheckResponseDTO handleProbeError(String hostId, String hostname) {
        CheckResponseDTO result = new CheckResponseDTO(
                hostId,
                hostname,
                StatusHosts.DISALLOWED,
                "Não passou pela probe ou erro de conexão.",
                null,
                null
        );

        saveHistory(hostId, hostname, StatusHosts.DISALLOWED.toString(), 0.0);

        return result;
    }

}

