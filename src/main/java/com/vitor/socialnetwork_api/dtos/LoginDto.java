package com.vitor.socialnetwork_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String email, @NotBlank String password) {
    
}