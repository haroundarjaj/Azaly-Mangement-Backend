package com.dartech.azalymanagementserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    private String id;

    private String ref;
    private String name;
    private String description;
    private float unitPrice;
    private float wholesalePrice;
    private String image;

    @DBRef
    private ProductCategory category;
}
