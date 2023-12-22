package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.OrderDetailsDTO;

public interface OrderDetailsService {

	public OrderDetailsDTO updateOrderDetails(AuthDTO authDTO, OrderDetailsDTO orderDetailsObj);

	public List<OrderDetailsDTO> getOrderHistory(AuthDTO authDTO, String fromDate, String toDate);

	public OrderDetailsDTO getOrderHistoryByCode(AuthDTO authDTO, OrderDetailsDTO orderDetails);
}
