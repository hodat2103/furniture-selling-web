package com.tadaboh.datn.furniture.selling.web.controllers.auth;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PermissionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PermissionResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RoleResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import com.tadaboh.datn.furniture.selling.web.services.IPermissionService;
import com.tadaboh.datn.furniture.selling.web.services.IRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}permissions")
@Tag(name = "Permission")
public class PermissionController {
    private final IPermissionService permissionService;
    private final IRoleService roleService;

    @PostMapping("/add-multiple")
    public ResponseEntity<ResponseSuccess> addPermissions(@RequestBody List<PermissionRequest> permissionRequests) {
        List<PermissionResponse> permissionResponses =  permissionService.addPermissions(permissionRequests);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.CREATED,
                "oke bro",
                permissionResponses
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @PostMapping("")
    public ResponseEntity<ResponseSuccess> create(@RequestBody PermissionRequest permissionRequest) {
        Permission insertedPermission = permissionService.create(permissionRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED,
                "Create permission successfully",
                insertedPermission);

        return ResponseEntity.ok(responseSuccess);
    }
    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<RoleResponse> addPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds
    ) {
        return ResponseEntity.ok(roleService.addPermissionsToRole(roleId, permissionIds));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSuccess> update(@PathVariable Long id, @RequestBody PermissionRequest permissionRequest) {
        Permission updatedPermission = permissionService.update(id, permissionRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK,
                "Update permission successfully",
                updatedPermission);

        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("")
    public ResponseEntity<ResponseSuccess> getAll() {
        Map<String, Map<String, List<String>>> permission = permissionService.loadPermissions();
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK,
                "Get permission successfully",
                permission);

        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccess> getById(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK,
                "Get permission successfully",
                permission);

        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSuccess> delete(@PathVariable Long id) {
        permissionService.delete(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK,
                "Delete permission successfully",
                null);

        return ResponseEntity.ok(responseSuccess);
    }
}
