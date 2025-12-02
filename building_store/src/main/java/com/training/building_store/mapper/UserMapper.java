package com.training.building_store.mapper;

import com.training.building_store.dto.user.RegisterRequestDTO;
import com.training.building_store.dto.user.RegisterResponseDTO;
import com.training.building_store.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "password", source = "password", qualifiedByName = "encode")
    User toEntity(RegisterRequestDTO dto);

    RegisterResponseDTO toResponse(User user);

    default RegisterResponseDTO toResponse(User user, String message) {
        if (user == null) return new RegisterResponseDTO(null, null, null, message);
        return new RegisterResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                message
        );
    }
}
