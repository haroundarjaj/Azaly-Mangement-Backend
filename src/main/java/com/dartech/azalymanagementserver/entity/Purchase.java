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
public class Purchase extends BaseEntity {
    @Id
    private String id;

    private String ref;
    private String date;
    private float totalAmount;
    private List<PurchasedFeedstock> purchasedFeedstock;

    @DBRef
    private Supplier supplier;
}
