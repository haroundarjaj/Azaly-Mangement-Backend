package com.dartech.azalymanagementserver.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductCategoryAddRequest {

    private String name;
    private String description;
    private String image;

}
