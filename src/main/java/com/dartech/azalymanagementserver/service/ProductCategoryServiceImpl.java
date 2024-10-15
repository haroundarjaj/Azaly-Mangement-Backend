package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ProductCategoryAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductCategoryUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductCategoryDto;
import com.dartech.azalymanagementserver.entity.ProductCategory;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductCategoryDto save(ProductCategoryAddRequest productCategoryAddRequest) {
        Optional<ProductCategory> testValue = productCategoryRepository.findByName(productCategoryAddRequest.getName());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Product type name already exist", "name_already_exist"));
        }
        ProductCategory productCategory = modelMapper.map(productCategoryAddRequest, ProductCategory.class);
        productCategory.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        productCategory = productCategoryRepository.save(productCategory);
        ProductCategoryDto productCategoryDto = modelMapper.map(productCategory, ProductCategoryDto.class);
        return productCategoryDto;
    }

    @Override
    public ProductCategoryDto update(ProductCategoryUpdateRequest productCategoryUpdateRequest) {
        Optional<ProductCategory> testValue = productCategoryRepository.findByName(productCategoryUpdateRequest.getName());
        if(testValue.isPresent() && !testValue.get().getId().equals(productCategoryUpdateRequest.getId())){
            throw new ApplicationException( new CustomError(400, "Product type name already exist", "name_already_exist"));
        }
        ProductCategory productCategory = modelMapper.map(productCategoryUpdateRequest, ProductCategory.class);
        System.out.println(productCategory);
        productCategory.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        productCategory.setLastModifiedDate(LocalDateTime.now().toString());
        productCategory = productCategoryRepository.save(productCategory);
        ProductCategoryDto productCategoryDto = modelMapper.map(productCategory, ProductCategoryDto.class);
        return productCategoryDto;
    }

    @Override
    public List<ProductCategoryDto> getAll() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtos = new ArrayList<>();
        for(ProductCategory productCategory : productCategories) {
            productCategoryDtos.add(modelMapper.map(productCategory, ProductCategoryDto.class));
        };
        return productCategoryDtos;
    }

    @Override
    public List<ProductCategoryDto> getAllWithoutImage() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtos = new ArrayList<>();
        for(ProductCategory productCategory : productCategories) {
            ProductCategoryDto productCategoryDto = modelMapper.map(productCategory, ProductCategoryDto.class);
            productCategoryDto.setImage("");
            productCategoryDtos.add(productCategoryDto);
        };
        return productCategoryDtos;
    }

    @Override
    public void delete(String id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public long countAllCategories() {
        return productCategoryRepository.count();
    }

}
