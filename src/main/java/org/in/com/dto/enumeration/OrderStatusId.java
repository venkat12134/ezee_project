package org.in.com.dto.enumeration;

public enum OrderStatusId {
	
	Initial("name"), 
	Inprogress("name"),
	Completed("name");
	
	public final String name;
	
	private OrderStatusId(String name) {
		this.name = name;
	}

}