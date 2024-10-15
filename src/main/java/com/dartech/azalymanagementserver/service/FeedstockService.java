package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.FeedstockAddRequest;
import com.dartech.azalymanagementserver.controller.Request.FeedstockUpdateRequest;
import com.dartech.azalymanagementserver.dto.FeedstockDto;

import java.util.List;

public interface FeedstockService {

    FeedstockDto save(FeedstockAddRequest feedstockAddRequest);

    FeedstockDto update(FeedstockUpdateRequest feedstockUpdateRequest);

    List<FeedstockDto> getAll();

    void delete(String id);
}
