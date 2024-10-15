package com.dartech.azalymanagementserver.payload.response;

import com.dartech.azalymanagementserver.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UserDto user;

	public JwtResponse(String accessToken, UserDto user) {
		this.token = accessToken;
		this.user = user;
	}
}
