package com.vitor.socialnetwork_api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreationDto(
    @NotBlank String name,
    @NotBlank String email,
    @NotBlank String password,
    String profilePicture,
    String banner, 
    @NotNull int followers
    ) {}
