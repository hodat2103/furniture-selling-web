package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RoleRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RoleResponse;
import java.util.List;
import java.util.Set;

public interface IRoleService {
    RoleResponse getByName(String name);
    RoleResponse getById(Long id);
    RoleResponse create(RoleRequest roleRequest);
    RoleResponse update(Long id, RoleRequest roleRequest);
    RoleResponse updatePermission(Long id, Set<Long> permissionIds);
    RoleResponse removePermissions(Long id, Set<Long> permissionIds);
    void delete(Long id);
    RoleResponse addPermissionsToRole(Long roleId, List<Long> permissionIds);
    RoleResponse createAndAddPermissionsToRole(Long roleId, List<PermissionRequest> permissionRequests);

}
