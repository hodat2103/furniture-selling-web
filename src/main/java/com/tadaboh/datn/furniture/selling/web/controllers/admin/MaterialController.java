package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.request.MaterialRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.models.products.Material;
import com.tadaboh.datn.furniture.selling.web.services.impl.MaterialService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}materials")
@RequiredArgsConstructor
@Tag(name = "Material", description = "Material API")
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping("")
    public ResponseEntity<ResponseSuccess> create(@RequestBody MaterialRequest materialRequest){
        Material material = materialService.create(materialRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.CREATED,
                "Material created successfully",
                material
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSuccess> update(@PathVariable Long id, @RequestBody MaterialRequest materialRequest){
        Material material = materialService.update(id,materialRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.ACCEPTED,
                "Material updated successfully",
                material
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSuccess> delete(@PathVariable Long id){
        materialService.delete(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Material deleted successfully",
                null
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccess> getById (@PathVariable Long id){
        Material material = materialService.getById(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Material retrieved successfully",
                material
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseSuccess> getById (@PathVariable(value = "name") String name){
        Material material = materialService.getByName(name);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Material retrieved successfully",
                material
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("")
    public ResponseEntity<ResponseSuccess> getById (){
        List<Material> materials = materialService.getAll();
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "All material retrieved successfully",
                materials
        );
        return ResponseEntity.ok(responseSuccess);
    }



}
