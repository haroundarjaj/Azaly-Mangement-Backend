package com.dartech.azalymanagementserver.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class ClientAddRequest {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String image;
    private String isIndividual;

}
