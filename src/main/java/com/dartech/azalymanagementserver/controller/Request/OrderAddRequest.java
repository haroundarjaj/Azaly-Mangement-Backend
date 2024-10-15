package com.dartech.azalymanagementserver.controller.Request;

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
public class OrderAddRequest {

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

}
