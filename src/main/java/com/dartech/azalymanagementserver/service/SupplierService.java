package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.SupplierAddRequest;
import com.dartech.azalymanagementserver.controller.Request.SupplierUpdateRequest;
import com.dartech.azalymanagementserver.dto.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto save(SupplierAddRequest supplierAddRequest);

    SupplierDto update(SupplierUpdateRequest supplierUpdateRequest);

    List<SupplierDto> getAll();

    void delete(String id);
}
