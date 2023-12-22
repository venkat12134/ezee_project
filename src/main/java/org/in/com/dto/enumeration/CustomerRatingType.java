package org.in.com.dto.enumeration;

public enum CustomerRatingType {

	New("name"), 
	Prime("name");
	
	public final String name;
	
	private CustomerRatingType(String name) {
		this.name = name;
	}
}
