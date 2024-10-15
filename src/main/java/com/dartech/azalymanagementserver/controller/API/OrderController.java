package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.OrderAddRequest;
import com.dartech.azalymanagementserver.controller.Request.OrderUpdateRequest;
import com.dartech.azalymanagementserver.dto.OrderDto;
import com.dartech.azalymanagementserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid OrderAddRequest orderAddRequest) {

        OrderDto order = orderService.save(orderAddRequest);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid OrderUpdateRequest orderUpdateRequest) {

        OrderDto order = orderService.update(orderUpdateRequest);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/update-state/{state}/{orderId}")
    public ResponseEntity upateState(@PathVariable String state, @PathVariable String orderId) {

        OrderDto order = orderService.updateState(state, orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<OrderDto> orders = orderService.getAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        orderService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/count-all")
    public ResponseEntity countAllOrders() {

        long countAll = orderService.countAllOrders();
        return new ResponseEntity<>(countAll, HttpStatus.OK);
    }

    @GetMapping("/count-delivered")
    public ResponseEntity countDeliveredOrders() {

        long countDelivered = orderService.countDeliveredOrders();
        return new ResponseEntity<>(countDelivered, HttpStatus.OK);
    }

    @GetMapping("/this-month-gains")
    public ResponseEntity getThisMonthGains() {

        float thisMonthGains = orderService.getThisMonthGains();
        return new ResponseEntity<>(thisMonthGains, HttpStatus.OK);
    }

    @GetMapping("/this-week-gains")
    public ResponseEntity getThisWeekGains() {

        float thisWeekGains = orderService.getThisWeekGains();
        return new ResponseEntity<>(thisWeekGains, HttpStatus.OK);
    }

    @GetMapping("/this-week-gains-statistics")
    public ResponseEntity getThisWeekGainsStatistics() {

        List<Float> thisWeekGainsStatistics = orderService.getThisWeekGainsStatistics();
        return new ResponseEntity<>(thisWeekGainsStatistics, HttpStatus.OK);
    }

    @GetMapping("/this-month-gains-statistics")
    public ResponseEntity getThisMonthGainsStatistics() {

        List<Float> thisMonthGainsStatistics = orderService.getThisMonthGainsStatistics();
        return new ResponseEntity<>(thisMonthGainsStatistics, HttpStatus.OK);
    }

    @GetMapping("/this-year-gains-statistics")
    public ResponseEntity getThisYearGainsStatistics() {

        List<Float> thisYearGainsStatistics = orderService.getThisYearGainsStatistics();
        return new ResponseEntity<>(thisYearGainsStatistics, HttpStatus.OK);
    }
}
