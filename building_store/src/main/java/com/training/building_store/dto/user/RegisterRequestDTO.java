package com.training.building_store.dto.user;

public record RegisterRequestDTO(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
