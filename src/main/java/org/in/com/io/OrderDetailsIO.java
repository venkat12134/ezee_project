package org.in.com.io;

import org.in.com.dto.enumeration.OrderStatusId;

import lombok.Data;

@Data
public class OrderDetailsIO {

	private String code;
	private NamespaceIO namespace;
	private CustomerIO customer;
	private String food;
	private int quantity;
	private String bookedAt;
	private String deliveryAt;
	private OrderStatusId statusId;
	private int transactionAmount;
	private int activeFlag;

}
