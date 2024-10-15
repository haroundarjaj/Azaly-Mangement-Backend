package com.dartech.azalymanagementserver.controller.Request;

import com.dartech.azalymanagementserver.entity.Feedstock;
import com.dartech.azalymanagementserver.entity.OrderLogInfo;
import com.dartech.azalymanagementserver.entity.OrderProduct;
import com.dartech.azalymanagementserver.entity.PurchasedFeedstock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PurchaseUpdateRequest {

    private String id;
    private String ref;
    private String date;
    private String supplierId;
    private float totalAmount;
    private List<PurchasedFeedstock> purchasedFeedstock;

}
