package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.ProductCategoryAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ProductCategoryUpdateRequest;
import com.dartech.azalymanagementserver.dto.ProductCategoryDto;
import com.dartech.azalymanagementserver.entity.ProductCategory;
import com.dartech.azalymanagementserver.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product/category")
@CrossOrigin("*")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productProductCategoryService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid ProductCategoryAddRequest productProductCategoryAddRequest) {

        ProductCategoryDto productProductCategory = productProductCategoryService.save(productProductCategoryAddRequest);
        return new ResponseEntity<>(productProductCategory, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid ProductCategoryUpdateRequest productProductCategoryUpdateRequest) {

        ProductCategoryDto productProductCategory = productProductCategoryService.update(productProductCategoryUpdateRequest);
        return new ResponseEntity<>(productProductCategory, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<ProductCategoryDto> productProductCategorys = productProductCategoryService.getAll();
        return new ResponseEntity<>(productProductCategorys, HttpStatus.OK);
    }

    @GetMapping("/all/no-image")
    public ResponseEntity getAllWithoutImage() {

        List<ProductCategoryDto> productProductCategorys = productProductCategoryService.getAllWithoutImage();
        return new ResponseEntity<>(productProductCategorys, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        productProductCategoryService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/count-all")
    public ResponseEntity countAllCategories() {

        long countAll = productProductCategoryService.countAllCategories();
        return new ResponseEntity<>(countAll, HttpStatus.OK);
    }
    
}
