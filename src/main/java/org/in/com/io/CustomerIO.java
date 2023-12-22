package org.in.com.io;

import org.in.com.dto.enumeration.CustomerRatingType;

import lombok.Data;

@Data
public class CustomerIO {

	private String code;
	private NamespaceIO namespace;
	private String address;
	private String firstName;
	private String lastName;
	private Long mobileNumber;
	private String emailId;
	private CustomerRatingType customerRatingType;
	private int activeFlag;
}
