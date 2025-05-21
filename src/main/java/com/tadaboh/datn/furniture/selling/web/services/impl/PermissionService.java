package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PermissionResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import com.tadaboh.datn.furniture.selling.web.repositories.PermissionRepository;
import com.tadaboh.datn.furniture.selling.web.services.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionResponse> addPermissions(List<PermissionRequest> permissionRequests) {
        List<Permission> permissions = permissionRequests.stream()
                .map(dto -> new Permission(null, dto.getName(), dto.getEndPoint(), dto.getHttpMethod()))
                .toList();

        List<Permission> savedPermissions = permissionRepository.saveAll(permissions);
        return savedPermissions.stream()
                .map(permission -> new PermissionResponse(permission.getId(), permission.getName(), permission.getEndPoint(), permission.getHttpMethod()))
                .toList();
    }

    @Override
    public Permission create(PermissionRequest permissionRequest) {
        Permission permission = new Permission();

        permission.setName(permissionRequest.getName());
        permission.setEndPoint(permissionRequest.getEndPoint());
        permission.setHttpMethod(permissionRequest.getHttpMethod());

        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Long id, PermissionRequest permissionRequest) {
        Permission existingPermission = permissionRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Permission not found"));
        if (!existingPermission.getName().equals(permissionRequest.getName()) &&
                permissionRepository.existsByName(permissionRequest.getName())) {
            throw new IllegalArgumentException("Permission with this name already exists");
        }
        existingPermission.setName(permissionRequest.getName());
        existingPermission.setEndPoint(permissionRequest.getEndPoint());
        existingPermission.setHttpMethod(permissionRequest.getHttpMethod());

        return permissionRepository.save(existingPermission);
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Permission not found"));
    }

    @Override
    public void delete(Long id) {
        Permission existingPermission = permissionRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Permission not found"));
        permissionRepository.delete(existingPermission);
    }
    public Map<String, Map<String, List<String>>> loadPermissions(){
        List<Permission> permission = permissionRepository.findAll();
        Map<String, Map<String, List<String>>> permissionMap = new HashMap<>();
        for (Permission permissionItem : permission) {
            permissionMap.computeIfAbsent(permissionItem.getEndPoint(), k-> new HashMap<>())
                    .computeIfAbsent(permissionItem.getHttpMethod(), k-> new ArrayList<>())
                    .add(permissionItem.getName());
        }
        return permissionMap;
    }
}
