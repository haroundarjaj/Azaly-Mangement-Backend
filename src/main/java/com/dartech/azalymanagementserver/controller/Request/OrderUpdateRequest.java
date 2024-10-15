package com.dartech.azalymanagementserver.controller.Request;

import com.dartech.azalymanagementserver.entity.OrderLogInfo;
import com.dartech.azalymanagementserver.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderUpdateRequest {

    private String id;
    private String ref;
    private String registeredDate;
    private String confirmedDate;
    private String shippedDate;
    private String deliveredDate;
    private String canceledDate;
    private String state;
    private String clientId;
    private float totalAmount;
    private float reduction;
    private List<OrderProduct> orderProducts;
    private List<OrderLogInfo> orderLog;

}
