package org.in.com.io;

import lombok.Data;

@Data
public class UserIO {

	private String code;
	private NamespaceIO namespace;
	private String userName;
	private String password;
	private String authtoken;
	private String firstName;
	private String lastName;
	private long mobileNumber;
	private String emailId;
	private int activeFlag;

}
