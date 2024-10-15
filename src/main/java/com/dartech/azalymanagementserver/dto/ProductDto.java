package com.dartech.azalymanagementserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private String id;

    private String ref;
    private String name;
    private String description;
    private float unitPrice;
    private float wholesalePrice;
    private String image;

    private String categoryId;
    private String categoryName;
}