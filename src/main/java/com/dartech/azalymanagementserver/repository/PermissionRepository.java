package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Permission;
import com.dartech.azalymanagementserver.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {

}
