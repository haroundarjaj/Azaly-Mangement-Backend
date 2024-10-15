package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String> {

    Optional<Purchase> findByRef(String ref);
}
