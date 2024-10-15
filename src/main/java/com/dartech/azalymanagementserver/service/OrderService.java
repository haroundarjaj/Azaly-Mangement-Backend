package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.OrderAddRequest;
import com.dartech.azalymanagementserver.controller.Request.OrderUpdateRequest;
import com.dartech.azalymanagementserver.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderAddRequest orderAddRequest);

    OrderDto update(OrderUpdateRequest orderUpdateRequest);

    OrderDto updateState(String state, String orderId);

    List<OrderDto> getAll();

    void delete(String id);

    long countAllOrders();

    long countDeliveredOrders();

    float getThisMonthGains();

    float getThisWeekGains();

    List<Float> getThisWeekGainsStatistics();

    List<Float> getThisMonthGainsStatistics();

    List<Float> getThisYearGainsStatistics();

}
