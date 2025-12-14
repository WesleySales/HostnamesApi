package com.wesley.ValidadorHostnames.entities;


import com.wesley.ValidadorHostnames.entities.dtos.CheckResponseDTO;
import java.time.Instant;

public class CacheEntry {

    private final CheckResponseDTO data;

    private Instant cacheEntryTime;

    public CacheEntry(CheckResponseDTO data) {
        this.data = data;
        this.cacheEntryTime = Instant.now();
    }

    public CheckResponseDTO getData() {
        return data;
    }

    public Instant getCacheEntryTime() {
        return cacheEntryTime;
    }

    public void setCacheEntryTime(Instant cacheEntryTime) {
        this.cacheEntryTime = cacheEntryTime;
    }
}