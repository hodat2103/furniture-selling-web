package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.request.SupplierRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.SupplierResponse;
import com.tadaboh.datn.furniture.selling.web.services.ISupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}suppliers")
@Tag(name = "Supplier")
public class SupplierController {
    private final ISupplierService supplierService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.create(supplierRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.CREATED,
                "Supplier created successfully",
                supplierResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.update(id, supplierRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Supplier updated successfully",
                supplierResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Supplier deleted successfully",
                null
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseSuccess> getByName(@PathVariable String name) {
        SupplierResponse supplierResponse = supplierService.getByName(name);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Supplier retrieved successfully",
                supplierResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("")
    public ResponseEntity<ResponseSuccess> getAll() {
        List<SupplierResponse> supplierResponses = supplierService.getAll();
        if (supplierResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "All supplier retrieved successfully",
                supplierResponses
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccess> getById(@PathVariable Long supplierId){
        SupplierResponse supplierResponse = supplierService.getById(supplierId);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Supplier retrieved successfully",
                supplierResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

}
