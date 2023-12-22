package org.in.com.dto;

import org.in.com.dto.enumeration.CustomerRatingType;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class CustomerDTO {
	private int id ;
	private String code;
	private NamespaceDTO namespace;
	private String address;
	private String firstName;
	private String lastName;
	private Long mobileNumber;
    private String emailId;
	private CustomerRatingType customerRatingType;
	private int activeFlag;
	private UserDTO updatedBy;
	private DateTime updatedAt;
}
