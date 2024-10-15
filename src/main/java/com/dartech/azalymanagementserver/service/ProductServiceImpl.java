package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ProductAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductDto;
import com.dartech.azalymanagementserver.entity.Product;
import com.dartech.azalymanagementserver.entity.ProductCategory;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDto save(ProductAddRequest productAddRequest) {
        Optional<Product> testValue = productRepository.findByRef(productAddRequest.getRef());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Product reference number already exist", "ref_already_exist"));
        }

        Product product = modelMapper.map(productAddRequest, Product.class);
        product.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<ProductCategory> category = productCategoryRepository.findByName(productAddRequest.getCategoryName());
        if(category.isPresent()) {
            product.setCategory(category.get());
        }
        product = productRepository.save(product);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public ProductDto update(ProductUpdateRequest productUpdateRequest) {
        Optional<Product> testValue = productRepository.findByRef(productUpdateRequest.getRef());
        if(testValue.isPresent() && !testValue.get().getId().equals(productUpdateRequest.getId())){
            throw new ApplicationException( new CustomError(400, "Product reference number already exist", "ref_already_exist"));
        }

        Product product = modelMapper.map(productUpdateRequest, Product.class);
        System.out.println(product);
        product.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        product.setLastModifiedDate(LocalDateTime.now().toString());
        Optional<ProductCategory> category = productCategoryRepository.findByName(productUpdateRequest.getCategoryName());
        if(category.isPresent()) {
            product.setCategory(category.get());
        }
        product = productRepository.save(product);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(modelMapper.map(product, ProductDto.class));
        };
        return productDtos;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public long countAllProducts() {
        return productRepository.count();
    }
}
