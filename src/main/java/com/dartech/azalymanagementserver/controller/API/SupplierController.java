package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.SupplierAddRequest;
import com.dartech.azalymanagementserver.controller.Request.SupplierUpdateRequest;
import com.dartech.azalymanagementserver.dto.SupplierDto;
import com.dartech.azalymanagementserver.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@CrossOrigin("*")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid SupplierAddRequest supplierAddRequest) {

        SupplierDto supplier = supplierService.save(supplierAddRequest);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid SupplierUpdateRequest supplierUpdateRequest) {

        SupplierDto supplier = supplierService.update(supplierUpdateRequest);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<SupplierDto> suppliers = supplierService.getAll();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        supplierService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
