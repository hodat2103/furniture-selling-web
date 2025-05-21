package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.MaterialRequest;
import com.tadaboh.datn.furniture.selling.web.models.products.Material;

import java.util.List;

public interface IMaterialService {
    Material create(MaterialRequest materialRequest);

    Material update(Long id, MaterialRequest materialRequest);

    Material getByName(String name);
    Material getById(Long id);
    List<Material> getAll();
    void delete(Long id);

}
