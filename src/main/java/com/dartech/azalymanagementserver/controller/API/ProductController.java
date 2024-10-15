package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.ProductAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductDto;
import com.dartech.azalymanagementserver.entity.Product;
import com.dartech.azalymanagementserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid ProductAddRequest productAddRequest) {

        ProductDto product = productService.save(productAddRequest);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {

        ProductDto product = productService.update(productUpdateRequest);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<ProductDto> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        productService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/count-all")
    public ResponseEntity countAllProducts() {

        long countAll = productService.countAllProducts();
        return new ResponseEntity<>(countAll, HttpStatus.OK);
    }
    
}
