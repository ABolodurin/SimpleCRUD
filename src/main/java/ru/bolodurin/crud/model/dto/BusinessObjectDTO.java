package ru.bolodurin.crud.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record BusinessObjectDTO(
        @Schema(description = "object ID")
        @Min(value = 1L)
        Long id,
        @Schema(description = "object name")
        @NotEmpty(message = "name must be not empty")
        String name,
        @Schema(description = "timestamp")
        LocalDateTime timestamp) {
}
