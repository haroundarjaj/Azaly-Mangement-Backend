package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ProductCategoryAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductCategoryUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryDto save(ProductCategoryAddRequest productCategoryAddRequest);

    ProductCategoryDto update(ProductCategoryUpdateRequest productCategoryUpdateRequest);

    List<ProductCategoryDto> getAll();

    List<ProductCategoryDto> getAllWithoutImage();

    void delete(String id);

    long countAllCategories();
}
