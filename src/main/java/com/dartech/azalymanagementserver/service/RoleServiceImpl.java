package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.RoleAddRequest;
import com.dartech.azalymanagementserver.controller.Request.RoleUpdateRequest;
import com.dartech.azalymanagementserver.dto.RoleDto;
import com.dartech.azalymanagementserver.entity.*;
import com.dartech.azalymanagementserver.entity.Role;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.PermissionRepository;
import com.dartech.azalymanagementserver.repository.RoleRepository;
import com.dartech.azalymanagementserver.repository.RoleRepository;
import com.dartech.azalymanagementserver.repository.UserRepository;
import com.dartech.azalymanagementserver.utils.StringDecryption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StringDecryption stringDecryption;

    @Override
    public RoleDto save(RoleAddRequest roleAddRequest) {
        Optional<Role> testValue = roleRepository.findByCode(roleAddRequest.getCode());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Role code already exist", "code_already_exist"));
        }
        testValue = roleRepository.findByName(roleAddRequest.getName());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Role name already exist", "name_already_exist"));
        }
        Role role = modelMapper.map(roleAddRequest, Role.class);
        List<Permission> permissions = new ArrayList<>();
        roleAddRequest.getPermissions().forEach(id -> {
            Optional<Permission> permission = permissionRepository.findById(id);
            if(permission.isPresent()) {
                permissions.add(permission.get());
            }
        });
        role.setPermissions(permissions);
        role = roleRepository.save(role);
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

    @Override
    public RoleDto update(RoleUpdateRequest roleUpdateRequest) {
        Role role = modelMapper.map(roleUpdateRequest, Role.class);
        List<Permission> permissions = new ArrayList<>();
        roleUpdateRequest.getPermissions().forEach(id -> {
            Optional<Permission> permission = permissionRepository.findById(id);
            if(permission.isPresent()) {
                permissions.add(permission.get());
            }
        });
        role.setPermissions(permissions);
        role = roleRepository.save(role);
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAllByCodeNot("SUP");
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(modelMapper.map(role, RoleDto.class));
        }
        ;
        return roleDtos;
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public boolean verifyRoleNotRelated(String id) {
        Role role = roleRepository.findById(id).get();
        List<User> users = userRepository.findAllByRolesContaining(role);

        return users.size() == 0;
    }

}
