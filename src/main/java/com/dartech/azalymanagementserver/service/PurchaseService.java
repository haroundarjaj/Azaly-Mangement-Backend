package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.PurchaseAddRequest;
import com.dartech.azalymanagementserver.controller.Request.PurchaseUpdateRequest;
import com.dartech.azalymanagementserver.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {

    PurchaseDto save(PurchaseAddRequest purchaseAddRequest);

    PurchaseDto update(PurchaseUpdateRequest purchaseUpdateRequest);

    List<PurchaseDto> getAll();

    void delete(String id);

    float getThisMonthCosts();

    float getThisWeekCosts();

    List<Float> getThisWeekCostsStatistics();

    List<Float> getThisMonthCostsStatistics();

    List<Float> getThisYearCostsStatistics();

}
