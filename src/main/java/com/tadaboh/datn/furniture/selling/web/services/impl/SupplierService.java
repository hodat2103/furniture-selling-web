package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.SupplierRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.SupplierResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.models.products.Supplier;
import com.tadaboh.datn.furniture.selling.web.repositories.SupplierRepository;
import com.tadaboh.datn.furniture.selling.web.services.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse getById(Long supplierId) {
        Supplier existingSupplier = supplierRepository.findById(supplierId).orElseThrow(
                () -> new DataNotFoundException("Not found supplier"));

        return SupplierResponse.fromSupplier(existingSupplier);
    }

    @Override
    public List<SupplierResponse> getAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        if (suppliers.isEmpty()){
            throw new DataNotFoundException("Supplier List is empty");
        }
        return mapToSupplierResponse(suppliers);
    }

    @Override
    public SupplierResponse getByName(String name) {
        if(name == null) {
            throw new DataNotFoundException("Supplier not found");
        }
        Supplier supplier = supplierRepository.findByName(name);
        return SupplierResponse.fromSupplier(supplier);
    }

    @Override
    public SupplierResponse create(SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();

        supplier.setName(supplierRequest.getName());
        supplier.setDescription(supplierRequest.getDescription());
        supplier.setIsActive(true);
        supplierRepository.save(supplier);
        return SupplierResponse.fromSupplier(supplier);
    }

    @Override
    public SupplierResponse update(Long id, SupplierRequest supplierRequest) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Supplier not found"));
        existingSupplier.setName(supplierRequest.getName());
        existingSupplier.setDescription(supplierRequest.getDescription());
        existingSupplier.setIsActive(supplierRequest.getIsActive());
        supplierRepository.save(existingSupplier);
        return SupplierResponse.fromSupplier(existingSupplier);
    }

    @Override
    public void delete(Long id) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Supplier not found"));
        existingSupplier.setIsActive(false);
        supplierRepository.save(existingSupplier);
    }

    private List<SupplierResponse> mapToSupplierResponse(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(SupplierResponse::fromSupplier)
                .collect(Collectors.toList());
    }
}
