package com.dartech.azalymanagementserver.dto;

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
public class UserDto {

    private String id;

    private String username;
    private String password;
    private boolean active;
    private boolean approved;

    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private String email;
    private String image;

    private String createdDate;
    private String createdBy;
    private String lastModifiedDate;
    private String lastModifiedBy;

    private List<String> roles;
}