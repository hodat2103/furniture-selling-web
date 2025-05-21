package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.SupplierRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.SupplierResponse;

import java.util.List;

public interface ISupplierService {
    SupplierResponse getById(Long supplierId);
    List<SupplierResponse> getAll ();
    SupplierResponse getByName(String name);
    SupplierResponse create(SupplierRequest supplierRequest);
    SupplierResponse update(Long id, SupplierRequest supplierRequest);
    void delete(Long id);
}
