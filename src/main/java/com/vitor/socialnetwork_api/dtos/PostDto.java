package com.vitor.socialnetwork_api.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record PostDto(@NotBlank String author, @NotBlank String imgPath, List<String> likes) {
    
}
