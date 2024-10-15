package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Feedstock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedstockRepository extends MongoRepository<Feedstock, String> {

    Optional<Feedstock> findByRef(String ref);
}
