package com.vitor.socialnetwork_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
    @NotBlank String name,
    String profilePicture,
    String banner
) {}