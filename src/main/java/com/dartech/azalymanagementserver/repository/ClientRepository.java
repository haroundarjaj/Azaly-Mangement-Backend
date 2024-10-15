package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
}
