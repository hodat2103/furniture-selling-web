package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RoleRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RoleResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.exceptions.ResourceNotFoundEx;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import com.tadaboh.datn.furniture.selling.web.models.users.Role;
import com.tadaboh.datn.furniture.selling.web.repositories.PermissionRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.RoleRepository;
import com.tadaboh.datn.furniture.selling.web.services.IRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public RoleResponse getByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new DataNotFoundException("Role not found");
        }
        return RoleResponse.fromRole(role);
    }

    @Override
    public RoleResponse getById(Long id) {
        Role existingRole =  roleRepository.findById(id).orElseThrow(()
         -> new DataNotFoundException("Role not found"));
        return RoleResponse.fromRole(existingRole);
    }

    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        Role role = new Role();

        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequest.getPermissionIds()));
        role.setPermissions(permissions);        role.setPermissions(permissions);

        Role insertedRole = roleRepository.save(role);
        return RoleResponse.fromRole(insertedRole);
    }

    @Override
    public RoleResponse update(Long id, RoleRequest roleRequest) {
        Role existingRole = roleRepository.findById(id).orElseThrow(()
         -> new DataNotFoundException("Role not found"));
        existingRole.setName(roleRequest.getName());
        existingRole.setDescription(roleRequest.getDescription());
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequest.getPermissionIds()));
        existingRole.setPermissions(permissions);

        Role updatedRole = roleRepository.save(existingRole);
        return RoleResponse.fromRole(updatedRole);
    }

    @Override
    public RoleResponse updatePermission(Long id, Set<Long> permissionIds) {
        Role existingRole = roleRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("Role not found"));

        Set<Permission> currentPermissions = existingRole.getPermissions();

        Set<Permission> newPermissions = new HashSet<>(permissionRepository.findAllById(permissionIds));

        currentPermissions.addAll(newPermissions);

        existingRole.setPermissions(currentPermissions);
        Role updatedRole = roleRepository.save(existingRole);

        return RoleResponse.fromRole(updatedRole);
    }

    @Override
    public RoleResponse removePermissions(Long id, Set<Long> permissionIds) {
        Role existingRole = roleRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("Role not found"));

        existingRole.getPermissions().removeIf(permission -> permissionIds.contains(permission.getId()));

        Role updatedRole = roleRepository.save(existingRole);
        return RoleResponse.fromRole(updatedRole);
    }

    @Override
    public void delete(Long id) {
        Role existingRole = roleRepository.findById(id).orElseThrow(()
         -> new DataNotFoundException("Role not found"));
        roleRepository.delete(existingRole);
    }

//    @Transactional
//    public RoleResponse addPermissionsToRole(Long roleId, Set<Long> permissionIds) {
//        // Find the role
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new ResourceNotFoundEx("Role not found: " + roleId));
//
//        // Fetch and validate permissions
//        Set<Permission> newPermissions = permissionIds.stream()
//                .map(permisionId -> permissionRepository.findById(permisionId)
//                        .orElseThrow(() -> new ResourceNotFoundEx("Permission not found: " + permisionId)))
//                .collect(Collectors.toSet());
//
//        // Add new permissions to existing role permissions
//        role.getPermissions().addAll(newPermissions);
//
//        // Save updated role
//        Role updatedRole = roleRepository.save(role);
//
//        // Return updated DTO
//        return RoleResponse.fromRole(updatedRole);
//    }\
    @Transactional
    public RoleResponse addPermissionsToRole(Long roleId, List<Long> permissionIds) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isEmpty()) {
            throw new DataNotFoundException("Role not found");
        }

        Role role = roleOpt.get();
        Set<Permission> permissions = permissionRepository.findByIdIn(permissionIds);
        role.setPermissions(permissions);
        Role rolePermission = roleRepository.save(role);
        return RoleResponse.fromRole(rolePermission);
    }
    @Transactional
    public RoleResponse createAndAddPermissionsToRole(Long roleId, List<PermissionRequest> permissionRequests) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isEmpty()) {
            throw new DataNotFoundException("Role not found");
        }
        Role role = roleOpt.get();

        // 1. Lưu danh sách quyền mới vào bảng permissions
        List<Permission> newPermissions = permissionRequests.stream()
                .map(req -> new Permission(null, req.getName(), req.getEndPoint(), req.getHttpMethod()))
                .collect(Collectors.toList());

        List<Permission> savedPermissions = permissionRepository.saveAll(newPermissions);

        // 2. Thêm quyền vào Role
        role.getPermissions().addAll(savedPermissions);
        Role updatedRole = roleRepository.save(role);

        return RoleResponse.fromRole(updatedRole);
    }

}
