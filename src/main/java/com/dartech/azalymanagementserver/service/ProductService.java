package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ProductAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductDto;
import com.dartech.azalymanagementserver.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto save(ProductAddRequest productAddRequest);

    ProductDto update(ProductUpdateRequest productUpdateRequest);

    List<ProductDto> getAll();

    void delete(String id);

    long countAllProducts();
}
