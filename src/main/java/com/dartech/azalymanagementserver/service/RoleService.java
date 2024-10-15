package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.RoleAddRequest;
import com.dartech.azalymanagementserver.controller.Request.RoleUpdateRequest;
import com.dartech.azalymanagementserver.dto.RoleDto;

import java.util.List;

public interface RoleService {
    
    RoleDto save(RoleAddRequest roleAddRequest);

    RoleDto update(RoleUpdateRequest roleUpdateRequest);

    List<RoleDto> getAll();

    void delete(String id);

    boolean verifyRoleNotRelated(String id);
}
