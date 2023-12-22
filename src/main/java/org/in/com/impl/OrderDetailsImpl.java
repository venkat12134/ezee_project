package org.in.com.impl;

import java.util.ArrayList;
import java.util.List;

import org.in.com.dao.OrderDetailsDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.OrderDetailsDTO;
import org.in.com.service.CustomerService;
import org.in.com.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsDAO orderDetailsDAO;

	@Autowired
	CustomerService customerService;

	@Override
	public OrderDetailsDTO updateOrderDetails(AuthDTO authDTO, OrderDetailsDTO orderDetailsObj) {
		try {
			CustomerDTO customer = new CustomerDTO();
			String customerObj = orderDetailsObj.getCustomer().getCode();
			customer.setCode(customerObj);
			customer = customerService.getCustomer(customer, authDTO);
			orderDetailsObj.setCustomer(customer);
			orderDetailsObj = orderDetailsDAO.updateOrderDetails(authDTO, orderDetailsObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetailsObj;
	}

	@Override
	public List<OrderDetailsDTO> getOrderHistory(AuthDTO authDTO, String fromDate, String toDate) {
		List<OrderDetailsDTO> orderDetailsDTO = new ArrayList<OrderDetailsDTO>();
		try {
			orderDetailsDTO = orderDetailsDAO.getOrderHistry(authDTO, fromDate, toDate);
			int customerObj = orderDetailsDTO.get(0).getCustomer().getId();
			List<Integer> customerDTO = new ArrayList<>();
			customerDTO.add(customerObj);
			List<CustomerDTO> customerDto = customerService.getCustomerByIdList(authDTO, customerDTO);
			if (customerDto != null && !customerDto.isEmpty()) {
				CustomerDTO customer = customerDto.get(0);
				customer.setId(customerObj);
				for (OrderDetailsDTO orderDetail : orderDetailsDTO) {
					orderDetail.setCustomer(customer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetailsDTO;
	}

	@Override
	public OrderDetailsDTO getOrderHistoryByCode(AuthDTO authDTO, OrderDetailsDTO orderDetails) {
		try {
			orderDetails = orderDetailsDAO.getOrderHistoryByCode(authDTO, orderDetails);
			CustomerDTO customer = new CustomerDTO();
			int customerId = orderDetails.getCustomer().getId();
			customer.setId(customerId);
			customer = customerService.getCustomerById(customer, authDTO);
			orderDetails.setCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderDetails;
	}

}
