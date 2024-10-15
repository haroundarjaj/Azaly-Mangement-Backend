package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.dto.PermissionDto;
import com.dartech.azalymanagementserver.entity.Permission;
import com.dartech.azalymanagementserver.repository.PermissionRepository;
import com.dartech.azalymanagementserver.utils.StringDecryption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PermissionDto> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionDtos.add(modelMapper.map(permission, PermissionDto.class));
        }
        ;
        return permissionDtos;
    }


}
