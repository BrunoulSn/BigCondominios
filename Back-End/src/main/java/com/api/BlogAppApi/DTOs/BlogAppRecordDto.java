package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record BlogAppRecordDto(@NotBlank String autor, @NotBlank String titulo, @NotBlank String texto) {
}
