package com.dartech.azalymanagementserver.dto;

import com.dartech.azalymanagementserver.entity.OrderProduct;
import com.dartech.azalymanagementserver.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderProductDto {

    private float price;
    private int quantity;

    private ProductDto product;
}