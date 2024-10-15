package com.dartech.azalymanagementserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
	
	@Id
	private String id;

	private String action;
	private String subject;

}
