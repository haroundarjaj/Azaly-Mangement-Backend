package com.dartech.azalymanagementserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
	
	@Id
	private String id;

	private String name;
	private String code;
	private String description;

	@DBRef
	private List<Permission> permissions;

	@Override
	public String getAuthority() {
		return code;
	}
}
