package com.vitor.socialnetwork_api.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(
    @NotBlank String name,
    String profilePicture,
    String banner,
    @NotNull List<String> followers
) {}