package com.training.building_store.controller;

import com.training.building_store.dto.user.AuthResponseDTO;
import com.training.building_store.dto.user.LoginRequestDTO;
import com.training.building_store.dto.user.RegisterRequestDTO;
import com.training.building_store.dto.user.RegisterResponseDTO;
import com.training.building_store.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
