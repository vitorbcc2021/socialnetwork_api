package com.vitor.socialnetwork_api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
    @NotBlank String name,
    @NotBlank String email,
    String password,
    String profilePicture,
    String banner, 
    @NotNull int followers
    ) {}
