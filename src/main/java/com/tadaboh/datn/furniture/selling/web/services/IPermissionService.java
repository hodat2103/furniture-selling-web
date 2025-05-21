package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PermissionResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IPermissionService {
    List<PermissionResponse> addPermissions(List<PermissionRequest> permissionRequests);
    Permission create(PermissionRequest permissionRequest);
    Permission update(Long id, PermissionRequest permissionRequest);
    Permission getById(Long id);
    void delete(Long id);
    Map<String, Map<String, List<String>>> loadPermissions();

}
