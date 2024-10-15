package com.dartech.azalymanagementserver;

import com.dartech.azalymanagementserver.entity.Permission;
import com.dartech.azalymanagementserver.entity.Role;
import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.repository.PermissionRepository;
import com.dartech.azalymanagementserver.repository.RoleRepository;
import com.dartech.azalymanagementserver.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication(scanBasePackages = {"com.dartech.*"})
@EnableMongoRepositories(basePackages = {"com.dartech.*"})
public class AzalyManagementServerApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PasswordEncoder encoder;

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AzalyManagementServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        generatePermissions();
        generateRoles();
        generateSuperAdmin();
    }

    @Transactional
    public void generatePermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        System.out.println(permissions.size());
        if(permissions.size() == 0){
            permissions.add(new Permission(null, "ACCESS", "CLIENT"));
            permissions.add(new Permission(null, "ADD", "CLIENT"));
            permissions.add(new Permission(null, "EDIT", "CLIENT"));
            permissions.add(new Permission(null, "DELETE", "CLIENT"));

            permissions.add(new Permission(null, "ACCESS", "ORDER"));
            permissions.add(new Permission(null, "ADD", "ORDER"));
            permissions.add(new Permission(null, "EDIT", "ORDER"));
            permissions.add(new Permission(null, "DELETE", "ORDER"));

            permissions.add(new Permission(null, "ACCESS", "SUPPLIER"));
            permissions.add(new Permission(null, "ADD", "SUPPLIER"));
            permissions.add(new Permission(null, "EDIT", "SUPPLIER"));
            permissions.add(new Permission(null, "DELETE", "SUPPLIER"));

            permissions.add(new Permission(null, "ACCESS", "PURCHASE"));
            permissions.add(new Permission(null, "ADD", "PURCHASE"));
            permissions.add(new Permission(null, "EDIT", "PURCHASE"));
            permissions.add(new Permission(null, "DELETE", "PURCHASE"));

            permissions.add(new Permission(null, "ACCESS", "FEEDSTOCK"));
            permissions.add(new Permission(null, "ADD", "FEEDSTOCK"));
            permissions.add(new Permission(null, "EDIT", "FEEDSTOCK"));
            permissions.add(new Permission(null, "DELETE", "FEEDSTOCK"));

            permissions.add(new Permission(null, "ACCESS", "PRODUCT"));
            permissions.add(new Permission(null, "ADD", "PRODUCT"));
            permissions.add(new Permission(null, "EDIT", "PRODUCT"));
            permissions.add(new Permission(null, "DELETE", "PRODUCT"));

            permissions.add(new Permission(null, "ACCESS", "PRODUCT_CATEGORY"));
            permissions.add(new Permission(null, "ADD", "PRODUCT_CATEGORY"));
            permissions.add(new Permission(null, "EDIT", "PRODUCT_CATEGORY"));
            permissions.add(new Permission(null, "DELETE", "PRODUCT_CATEGORY"));

            permissions.add(new Permission(null, "ACCESS", "USER"));
            permissions.add(new Permission(null, "EDIT", "USER"));
            permissions.add(new Permission(null, "DELETE", "USER"));

            permissions.add(new Permission(null, "ACCESS", "ROLE"));
            permissions.add(new Permission(null, "ADD", "ROLE"));
            permissions.add(new Permission(null, "EDIT", "ROLE"));
            permissions.add(new Permission(null, "DELETE", "ROLE"));
            permissionRepository.saveAll(permissions);
        }
    }

    public void generateRoles() {
        Optional<Role> role = roleRepository.findByCode("SUP");
        if(!role.isPresent()){
            Role role1 = new Role();
            role1.setName("Super admin");
            role1.setCode("SUP");
            role1.setDescription("Super admin role granted all the privileges in the app");
            List<Permission> permissions = permissionRepository.findAll();
            role1.setPermissions(permissions);
            roleRepository.save(role1);
        }
        role = roleRepository.findByCode("US");
        if(!role.isPresent()){
            Role role2 = new Role();
            role2.setName("User");
            role2.setCode("US");
            role2.setDescription("Normal user that has access to the basic options in the app");
            roleRepository.save(role2);
        }
    }

    public void generateSuperAdmin() {
        Optional<User> user = userRepository.findByEmail("super@admin.com");
        if (!user.isPresent()){
            User newUser = new User();
            newUser.setFirstName("Super");
            newUser.setLastName("Admin");
            newUser.setEmail("super@admin.com");
            newUser.setUsername("super@admin.com");
            newUser.setPassword(encoder.encode("azalySuperAdmin"));
            newUser.setActive(true);
            newUser.setApproved(true);
            newUser.setCreatedBy("System");
            newUser.setCreatedDate(new Date().toString());
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByCode("SUP").get());
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }
    }
}
