package com.dartech.azalymanagementserver.dto;

import com.dartech.azalymanagementserver.entity.Feedstock;
import com.dartech.azalymanagementserver.entity.OrderLogInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseDto {

    private String id;

    private String ref;
    private String date;
    private float totalAmount;
    private List<PurchasedFeedstockDto> purchasedFeedstock;

    private String supplierId;
    private String supplierFirstName;
    private String supplierLastName;
}