package com.wesley.ValidadorHostnames.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record HistoryRequestDTO(

        @NotEmpty
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataInicio,

        @NotEmpty
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataFim

) {
}
