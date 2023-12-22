package org.in.com.dto;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class UserDTO {

	private int id;
	private String code;
	private NamespaceDTO namespace;
	private String userName;
	private String password;
	private String authtoken;
	private String firstName;
	private String lastName;
	private long mobileNumber;
	private String emailId;
	private int activeFlag;
	private UserDTO updatedBy;
	private DateTime updatedAt;
}
