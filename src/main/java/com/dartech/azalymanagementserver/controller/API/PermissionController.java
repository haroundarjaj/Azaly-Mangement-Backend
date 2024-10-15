package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.dto.PermissionDto;
import com.dartech.azalymanagementserver.dto.RoleDto;
import com.dartech.azalymanagementserver.entity.Permission;
import com.dartech.azalymanagementserver.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin("*")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<PermissionDto> permissions = permissionService.getAll();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }
}
