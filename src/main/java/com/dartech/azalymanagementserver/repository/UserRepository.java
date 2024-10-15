package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.entity.Role;
import com.dartech.azalymanagementserver.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    List<User> findAllByUsernameNot(String username);

    List<User> findAllByApprovedAndUsernameNot(boolean state, String username);

    List<User> findAllByRolesContaining(Role role);

}
