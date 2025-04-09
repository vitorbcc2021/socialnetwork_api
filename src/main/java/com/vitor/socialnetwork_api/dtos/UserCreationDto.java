package com.vitor.socialnetwork_api.dtos;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreationDto(
    @NotBlank String name,
    @NotBlank String email,
    @NotBlank String password,
    String profilePicture,
    String banner,
    @NotNull List<String> followers
    ) {}
