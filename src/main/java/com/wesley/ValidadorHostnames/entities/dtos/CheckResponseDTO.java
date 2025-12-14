package com.wesley.ValidadorHostnames.entities.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wesley.ValidadorHostnames.entities.enums.StatusHosts;

import java.util.List;

public record CheckResponseDTO(

        String hostname,
        StatusHosts status,
        String description,

        @JsonIgnore
        List<String> ips,

        @JsonIgnore
        Long rtt
) {



}
