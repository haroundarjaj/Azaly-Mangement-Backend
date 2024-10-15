package com.dartech.azalymanagementserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity {

    @CreatedDate
    @Builder.Default
    private String createdDate = LocalDateTime.now().toString();

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private String lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
