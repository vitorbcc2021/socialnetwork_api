package com.vitor.socialnetwork_api.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record FollowDto(@NotNull UUID followerId) {}