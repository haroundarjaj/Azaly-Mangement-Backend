package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByRef(String ref);
}
