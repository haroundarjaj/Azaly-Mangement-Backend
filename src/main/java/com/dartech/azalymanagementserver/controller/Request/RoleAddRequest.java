package com.dartech.azalymanagementserver.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleAddRequest {

    private String name;
    private String code;
    private String description;

    private List<String> permissions;

}
