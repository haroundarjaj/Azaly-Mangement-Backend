package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.PurchaseAddRequest;
import com.dartech.azalymanagementserver.controller.Request.PurchaseUpdateRequest;
import com.dartech.azalymanagementserver.dto.PurchaseDto;
import com.dartech.azalymanagementserver.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@CrossOrigin("*")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid PurchaseAddRequest purchaseAddRequest) {

        PurchaseDto purchase = purchaseService.save(purchaseAddRequest);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid PurchaseUpdateRequest purchaseUpdateRequest) {

        PurchaseDto purchase = purchaseService.update(purchaseUpdateRequest);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<PurchaseDto> purchases = purchaseService.getAll();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        purchaseService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/this-month-costs")
    public ResponseEntity getThisMonthCosts() {

        float thisMonthCosts = purchaseService.getThisMonthCosts();
        return new ResponseEntity<>(thisMonthCosts, HttpStatus.OK);
    }

    @GetMapping("/this-week-costs")
    public ResponseEntity getThisWeekCosts() {

        float thisWeekCosts = purchaseService.getThisWeekCosts();
        return new ResponseEntity<>(thisWeekCosts, HttpStatus.OK);
    }

    @GetMapping("/this-week-costs-statistics")
    public ResponseEntity getThisWeekCostsStatistics() {

        List<Float> thisWeekCostsStatistics = purchaseService.getThisWeekCostsStatistics();
        return new ResponseEntity<>(thisWeekCostsStatistics, HttpStatus.OK);
    }

    @GetMapping("/this-month-costs-statistics")
    public ResponseEntity getThisMonthCostsStatistics() {

        List<Float> thisMonthCostsStatistics = purchaseService.getThisMonthCostsStatistics();
        return new ResponseEntity<>(thisMonthCostsStatistics, HttpStatus.OK);
    }

    @GetMapping("/this-year-costs-statistics")
    public ResponseEntity getThisYearCostsStatistics() {

        List<Float> thisYearCostsStatistics = purchaseService.getThisYearCostsStatistics();
        return new ResponseEntity<>(thisYearCostsStatistics, HttpStatus.OK);
    }
    
}
