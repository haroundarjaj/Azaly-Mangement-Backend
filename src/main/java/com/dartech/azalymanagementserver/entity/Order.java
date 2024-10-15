package com.dartech.azalymanagementserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
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
    private List<OrderProduct> orderProducts;
    private List<OrderLogInfo> orderLog = new ArrayList<>();

    @DBRef
    private Client client;
}
