package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.SupplierAddRequest;
import com.dartech.azalymanagementserver.controller.Request.SupplierUpdateRequest;
import com.dartech.azalymanagementserver.dto.SupplierDto;
import com.dartech.azalymanagementserver.entity.Supplier;
import com.dartech.azalymanagementserver.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SupplierDto save(SupplierAddRequest supplierAddRequest) {
        Supplier supplier = modelMapper.map(supplierAddRequest, Supplier.class);
        supplier.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        supplier = supplierRepository.save(supplier);
        SupplierDto supplierDto = modelMapper.map(supplier, SupplierDto.class);
        return supplierDto;
    }

    @Override
    public SupplierDto update(SupplierUpdateRequest supplierUpdateRequest) {
        Supplier supplier = modelMapper.map(supplierUpdateRequest, Supplier.class);
        System.out.println(supplier);
        supplier.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        supplier.setLastModifiedDate(LocalDateTime.now().toString());
        supplier = supplierRepository.save(supplier);
        SupplierDto supplierDto = modelMapper.map(supplier, SupplierDto.class);
        return supplierDto;
    }

    @Override
    public List<SupplierDto> getAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDto> supplierDtos = new ArrayList<>();
        for(Supplier supplier : suppliers) {
            supplierDtos.add(modelMapper.map(supplier, SupplierDto.class));
        };
        return supplierDtos;
    }

    @Override
    public void delete(String id) {
        supplierRepository.deleteById(id);
    }
}
