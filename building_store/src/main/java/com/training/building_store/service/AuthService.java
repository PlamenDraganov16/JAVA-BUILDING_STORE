package com.training.building_store.service;

import com.training.building_store.dto.user.AuthResponseDTO;
import com.training.building_store.dto.user.LoginRequestDTO;
import com.training.building_store.dto.user.RegisterRequestDTO;
import com.training.building_store.dto.user.RegisterResponseDTO;
import com.training.building_store.mapper.UserMapper;
import com.training.building_store.model.User;
import com.training.building_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO register(RegisterRequestDTO dto) {

        if (userRepository.existsByUsername(dto.username()))
            return userMapper.toResponse(null, "Username already in use");
        if (userRepository.existsByEmail(dto.email()))
            return userMapper.toResponse(null, "Email already in use");

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved, "User registered successfully");
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByUsername(dto.username()).orElse(null);

        if (user == null)
            return new AuthResponseDTO("User not found", null);

        if (!passwordEncoder.matches(dto.password(), user.getPassword()))
            return new AuthResponseDTO("Invalid username or password", null);

        // No JWT, no authentication — just return success
        return new AuthResponseDTO("Login successful", null);
    }
}
