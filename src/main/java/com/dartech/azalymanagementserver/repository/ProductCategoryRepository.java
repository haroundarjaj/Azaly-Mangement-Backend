package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {

    Optional<ProductCategory> findByName (String name);
}
