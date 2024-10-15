package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.entity.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
}
