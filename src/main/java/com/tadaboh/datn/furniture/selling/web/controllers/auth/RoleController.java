package com.tadaboh.datn.furniture.selling.web.controllers.auth;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RoleRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RoleResponse;
import com.tadaboh.datn.furniture.selling.web.services.IRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}roles")
@Tag(name = "Role", description = "Role API")
public class RoleController {
    private final IRoleService roleService;

    @PostMapping("")
    public ResponseEntity<ResponseSuccess> create(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.create(roleRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Role created successfully", roleResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseSuccess);
    }
    @PostMapping("/{roleId}/permissions/create-add")
    public ResponseEntity<RoleResponse> createAndAddPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody List<PermissionRequest> permissionRequests) {
        RoleResponse response = roleService.createAndAddPermissionsToRole(roleId, permissionRequests);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{roleId}/permissions/add")
    public ResponseEntity<RoleResponse> addPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds) {
        RoleResponse response = roleService.addPermissionsToRole(roleId, permissionIds);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSuccess> update(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.update(id, roleRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Role updated successfully", roleResponse);
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseSuccess> getByName(@PathVariable String name) {
        RoleResponse roleResponse = roleService.getByName(name);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Role retrieved successfully", roleResponse);
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccess> getById(@PathVariable Long id) {
        RoleResponse roleResponse = roleService.getById(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Role retrieved successfully", roleResponse);
        return ResponseEntity.ok(responseSuccess);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSuccess> delete(@PathVariable Long id) {
        roleService.delete(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Role deleted successfully", null);
        return ResponseEntity.ok(responseSuccess);
    }
}
