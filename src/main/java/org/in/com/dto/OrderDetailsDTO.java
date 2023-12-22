package org.in.com.dto;

import org.in.com.dto.enumeration.OrderStatusId;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class OrderDetailsDTO {

	private int id;
	private String code;
	private NamespaceDTO namespace;
	private CustomerDTO customer;
	private String food;
	private int quantity;
	private DateTime bookedAt;
	private DateTime deliveryAt;
	private OrderStatusId statusId;
	private int transactionAmount;
	private int activeFlag;
	private UserDTO updatedBy;
	private DateTime updatedAt;
}
