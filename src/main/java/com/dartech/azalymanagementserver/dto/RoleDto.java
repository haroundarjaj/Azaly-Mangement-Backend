package com.dartech.azalymanagementserver.dto;

import com.dartech.azalymanagementserver.entity.Permission;
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
public class RoleDto {

    private String id;

    private String name;
    private String code;
    private String description;

    private List<Permission> permissions;
}