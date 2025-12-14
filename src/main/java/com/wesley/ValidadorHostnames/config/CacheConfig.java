package com.wesley.ValidadorHostnames.config;

import com.wesley.ValidadorHostnames.services.WhitelistService;
import com.wesley.ValidadorHostnames.entities.CacheEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Value("${app.cache.ttl-seconds:300}") //ajustei pra 5 minutos
    private long cacheTtlSeconds;

    @Bean
    public long cacheTtlSeconds() {
        return this.cacheTtlSeconds;
    }

    @Bean
    public Map<String, CacheEntry> hostnameCache(WhitelistService whitelistService) {

//        long totalHosts = whitelistService.countTotalAllowedHosts();
        long totalHosts = 20;

        int MAX_CACHE_SIZE = (int) Math.max(1, totalHosts * 0.10);

        System.out.println("------------------------------------------------------------------");
        System.out.println("  CONFIGURAÇÃO DE CACHE FINALIZADA:");
        System.out.println("  - Total de Hosts no Banco: " + totalHosts);
        System.out.println("  - Limite de Cache (10%): " + MAX_CACHE_SIZE);
        System.out.println("  - TTL: " + cacheTtlSeconds + " segundos");
        System.out.println("------------------------------------------------------------------");


        return Collections.synchronizedMap(new LinkedHashMap<String, CacheEntry>(
                MAX_CACHE_SIZE, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        });
    }
}