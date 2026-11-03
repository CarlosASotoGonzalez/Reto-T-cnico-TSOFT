package com.example.tsoft.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PetRequest(
        @NotNull(message = "id es obligatorio")
        @Positive(message = "id debe ser positivo")
        Long id,

        @NotBlank(message = "status es obligatorio")
        String status,

        @NotBlank(message = "name es obligatorio")
        String name
) {}