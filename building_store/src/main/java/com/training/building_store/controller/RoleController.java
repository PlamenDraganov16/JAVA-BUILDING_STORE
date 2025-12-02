package com.training.building_store.controller;

import com.training.building_store.dto.role.RoleRequestDTO;
import com.training.building_store.dto.role.RoleResponseDTO;
import com.training.building_store.model.Role;
import com.training.building_store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleRequestDTO dto) {
        Role created = roleService.createRole(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(@RequestBody RoleResponseDTO dto) {
        roleService.assignRoleToUser(dto.username(), dto.roleName());
        return ResponseEntity.ok("Role assigned");
    }
}
