package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.MaterialRequest;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.models.products.Material;
import com.tadaboh.datn.furniture.selling.web.repositories.MaterialRepository;
import com.tadaboh.datn.furniture.selling.web.services.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService implements IMaterialService {

    private  final MaterialRepository materialRepository;
    @Override
    public Material create(MaterialRequest materialRequest) {
        Material material = new Material();
        material.setName(materialRequest.getName());

        materialRepository.save(material);
        return material;
    }

    @Override
    public Material update(Long id, MaterialRequest materialRequest) {
        Material existingMaterial = materialRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Material not found"));

        existingMaterial.setName(materialRequest.getName());

        materialRepository.save(existingMaterial);
        return existingMaterial;
    }

    @Override
    public Material getByName(String name) {
        if (name == null) {
            throw new DataNotFoundException("Material not found");
        }
        return materialRepository.findByName(name);
    }

    @Override
    public Material getById(Long id) {
        return materialRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Not found material")
        );
    }

    @Override
    public List<Material> getAll() {
        return materialRepository.findAll();
    }


    @Override
    public void delete(Long id) {
        Material existingMaterial = materialRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Material not found"));
        materialRepository.delete(existingMaterial);
    }
}
