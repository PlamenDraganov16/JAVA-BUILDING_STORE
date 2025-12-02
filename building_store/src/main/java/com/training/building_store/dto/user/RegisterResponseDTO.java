package com.training.building_store.dto.user;

public record RegisterResponseDTO(
        Long id,
        String username,
        String email,
        String message
) {
}
