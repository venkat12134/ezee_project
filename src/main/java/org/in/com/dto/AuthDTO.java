package org.in.com.dto;

import lombok.Data;

@Data
public class AuthDTO {

	private UserDTO user;
	private NamespaceDTO namespace;
	private String authtoken; 
}
