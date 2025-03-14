package com.vitor.socialnetwork_api.dtos;

import com.vitor.socialnetwork_api.models.UserModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostDto(@NotNull UserModel userModel, @NotBlank String imgPath, @NotNull int likes) {
    
}
