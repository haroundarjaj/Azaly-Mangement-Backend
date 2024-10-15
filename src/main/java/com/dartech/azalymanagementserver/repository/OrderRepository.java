package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.entity.Order;
import com.dartech.azalymanagementserver.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByRef(String ref);

    long countDistinctByStateEquals(String state);
}
