//package com.wesley.ValidadorHostnames.services.Postgre;
//
//import com.wesley.ValidadorHostnames.entities.dtos.CheckResponseDTO;
//import com.wesley.ValidadorHostnames.entities.enums.StatusHosts;
//import com.wesley.ValidadorHostnames.entities.CacheEntry;
//import com.wesley.ValidadorHostnames.services.ProbeService;
//import com.wesley.ValidadorHostnames.services.dtos.ProbeResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Service("hostnameCheckPostgreService")
//public class HostnameCheckService {
//
//    private final Map<String, CacheEntry> hostnameCache;
//    private final long cacheTtlSeconds;
//
//    @Autowired
//    private WhitelistService whitelistService;
//    @Autowired
//    private ProbeService probeService;
//
//    @Autowired
//    public HostnameCheckService(Map<String, CacheEntry> hostnameCache, long cacheTtlSeconds) {
//        this.hostnameCache = hostnameCache;
//        this.cacheTtlSeconds = cacheTtlSeconds;
//    }
//
//
//    public CheckResponseDTO check(String hostname) {
//
//        CheckResponseDTO cachedResult = getFromCache(hostname);
//
//        if (cachedResult != null) {
//            return new CheckResponseDTO(
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
//        if (!whitelistService.isAllowed(hostname)) {
//            finalResult = new CheckResponseDTO(
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
//                        hostname,
//                        StatusHosts.VALID,
//                        "Hostname encontrado e válido",
//                        probe.ips(),
//                        probe.rtt()
//                );
//            } catch (Exception e) {
//                finalResult = new CheckResponseDTO(
//                        hostname,
//                        StatusHosts.DISALLOWED,
//                        "Não passou pela probe.",
//                        null,
//                        null
//                );
//            }
//        }
//
//        putInCache(hostname, finalResult);
//        return finalResult;
//    }
//
//
//    public CheckResponseDTO check2(String hostname) {
//
//        CheckResponseDTO cachedResult = getFromCache(hostname);
//
//        if (cachedResult != null) {
//            return new CheckResponseDTO(
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
//        if (!whitelistService.isAllowedOtimizada(hostname)) {
//            finalResult = new CheckResponseDTO(
//                    hostname,
//                    StatusHosts.UNKNOWN,
//                    "Hostname não encontrado no banco",
//                    null,
//                    null
//            );
//        } else {
//            try {
//                ProbeResult probe = probeService.probe(hostname);
//                finalResult = new CheckResponseDTO(
//                        hostname,
//                        StatusHosts.VALID,
//                        "Hostname exato encontrado",
//                        probe.ips(),
//                        probe.rtt()
//                );
//            } catch (Exception e) {
//                finalResult = new CheckResponseDTO(
//                        hostname,
//                        StatusHosts.DISALLOWED,
//                        "Hostname encontrado com wildcard.",
//                        null,
//                        null
//                );
//            }
//        }
//
//        putInCache(hostname, finalResult);
//        return finalResult;
//    }
//
//
//    private CheckResponseDTO getFromCache(String hostname) {
//        CacheEntry entry = hostnameCache.get(hostname);
//
//        if (entry != null) {
//
//            if (entry.getCacheEntryTime().plusSeconds(this.cacheTtlSeconds).isBefore(Instant.now())) {
//                System.out.println("Cache expirado (TTL): " + hostname);
//                hostnameCache.remove(hostname);
//                return null;
//            }
//
//            entry.setCacheEntryTime(Instant.now());
//            return entry.getData();
//        }
//        return null;
//    }
//
//    private void putInCache(String hostname, CheckResponseDTO result) {
//        // Apenas hosts encontrados no banco serão guardados no cache
//        if (!result.status().equals(StatusHosts.UNKNOWN)) {
//            CacheEntry entry = new CacheEntry(result);
//            hostnameCache.put(hostname, entry);
//        }
//    }
//
//    public List<CheckResponseDTO> getResultsCache(){
//
//        List<CacheEntry> entries = new ArrayList<>(hostnameCache.values());
//
//        List<CheckResponseDTO> results = new ArrayList<>(entries.size());
//
//        for (CacheEntry entry : entries) {
//            results.add(entry.getData());
//        }
//
//        return results;
//
//    }
//}
//
