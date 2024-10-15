package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.RoleAddRequest;
import com.dartech.azalymanagementserver.controller.Request.RoleUpdateRequest;
import com.dartech.azalymanagementserver.dto.RoleDto;
import com.dartech.azalymanagementserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin("*")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid RoleAddRequest roleAddRequest) {

        RoleDto role = roleService.save(roleAddRequest);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid RoleUpdateRequest roleUpdateRequest) {

        RoleDto role = roleService.update(roleUpdateRequest);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<RoleDto> roles = roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        roleService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity verifyRoleNotRelated(@PathVariable String id) {

        boolean notRelated = roleService.verifyRoleNotRelated(id);
        return new ResponseEntity<>(notRelated, HttpStatus.OK);
    }
}
