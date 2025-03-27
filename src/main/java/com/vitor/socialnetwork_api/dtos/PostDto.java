package com.vitor.socialnetwork_api.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record PostDto(@NotBlank String authorId, @NotBlank String imgPath, List<String> likes) {
    
}
