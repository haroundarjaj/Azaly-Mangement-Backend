package com.dartech.azalymanagementserver.dto;

import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.entity.OrderLogInfo;
import com.dartech.azalymanagementserver.entity.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private String id;

    private String ref;
    private String registeredDate;
    private String confirmedDate;
    private String shippedDate;
    private String deliveredDate;
    private String canceledDate;
    private String state;
    private float totalAmount;
    private float reduction;
    private List<OrderProductDto> orderProducts;
    private List<OrderLogInfo> orderLog;

    private String clientId;
    private String clientFirstName;
    private String clientLastName;
}