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
public class UserUpdateRequest {

    private String id;
    private String username;
    private String password;
    private boolean active;

    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private String email;
    private String image;
    private List<String> roles;

}
