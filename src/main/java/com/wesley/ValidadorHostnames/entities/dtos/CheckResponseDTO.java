package com.wesley.ValidadorHostnames.entities.dtos;

import com.wesley.ValidadorHostnames.entities.enums.StatusHosts;

import java.util.List;

public record CheckResponseDTO(
        String hostname,
        StatusHosts status,
        String description,
        List<String> ips,
        Long rtt
) {

}
