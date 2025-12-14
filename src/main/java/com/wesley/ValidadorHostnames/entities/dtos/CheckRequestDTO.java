package com.wesley.ValidadorHostnames.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CheckRequestDTO(

        @Schema(
                description = "Lista de hostnames a serem validados",
                example = "[\"example.com\", \"api.example.com\", \"google.com\",\"meusite.com.br\"]"
        )
        @NotEmpty
        List<String> hostnames
) {
}
