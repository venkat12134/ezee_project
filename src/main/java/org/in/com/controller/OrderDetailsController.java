package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.OrderDetailsDTO;
import org.in.com.io.CustomerIO;
import org.in.com.io.OrderDetailsIO;
import org.in.com.service.AuthService;
import org.in.com.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hirondelle.date4j.DateTime;

@Controller
@RequestMapping("/{authtoken}/order/details")
public class OrderDetailsController {

	
	@Autowired
	OrderDetailsService orderDetailService;
	
	@Autowired
	AuthService authService; 

	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderDetailsIO updateOrderDetails(@PathVariable("authtoken") String authtoken, @RequestBody OrderDetailsIO orderDetailsIO) throws Exception {
		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		OrderDetailsIO orderDetailsIo = new OrderDetailsIO();
		OrderDetailsDTO orderDetailsObj = new OrderDetailsDTO();
		if (authDTO != null) {
			orderDetailsObj.setCode(orderDetailsIO.getCode());
			String customerCode = orderDetailsIO.getCustomer().getCode();
			CustomerDTO customer = new CustomerDTO();
			customer.setCode(customerCode);
			orderDetailsObj.setCustomer(customer);
			orderDetailsObj.setFood(orderDetailsIO.getFood());
			orderDetailsObj.setQuantity(orderDetailsIO.getQuantity());
			String bookedAt = orderDetailsIO.getBookedAt();
			DateTime bookedAtObj = new DateTime(bookedAt);
			orderDetailsObj.setBookedAt(bookedAtObj);
			String deliveryAt = orderDetailsIO.getDeliveryAt();
			DateTime deliveryAtObj = new DateTime(deliveryAt);
			orderDetailsObj.setDeliveryAt(deliveryAtObj);
			orderDetailsObj.setStatusId(orderDetailsIO.getStatusId());
			orderDetailsObj.setTransactionAmount(orderDetailsIO.getTransactionAmount());
			orderDetailsObj.setActiveFlag(orderDetailsIO.getActiveFlag());
			OrderDetailsDTO namespaceDTO = orderDetailService.updateOrderDetails(authDTO, orderDetailsObj);
			orderDetailsIo.setActiveFlag(namespaceDTO.getActiveFlag());
			orderDetailsIo.setCode(namespaceDTO.getCode());
		}
		return orderDetailsIo;
	}
	@RequestMapping(value = "/history/{from}/{to}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<OrderDetailsIO> getOrderHistory(@PathVariable("authtoken") String authtoken, @PathVariable("from") String fromDate, @PathVariable("to") String toDate) throws Exception {
		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		List<OrderDetailsIO> orderDetailsIo = new ArrayList<OrderDetailsIO>();
		List<OrderDetailsDTO> orderDetailsObj = new ArrayList<>();
		if (authDTO != null) {
			orderDetailsObj = orderDetailService.getOrderHistory(authDTO, fromDate, toDate);
			for (OrderDetailsDTO orderDetailsDTO : orderDetailsObj) {
				OrderDetailsIO orderDetailsIO = new OrderDetailsIO();
				orderDetailsIO.setCode(orderDetailsDTO.getCode());
				String customer = orderDetailsDTO.getCustomer().getCode();
				CustomerIO customerIO = new CustomerIO();
				customerIO.setCode(customer);
				orderDetailsIO.setCustomer(customerIO);
				orderDetailsIO.setFood(orderDetailsDTO.getFood());
				orderDetailsIO.setQuantity(orderDetailsDTO.getQuantity());
				DateTime bookedAt = orderDetailsDTO.getBookedAt();
				String bookedAtObj = bookedAt.format("YYYY-MM-DD hh:mm:ss");
				orderDetailsIO.setBookedAt(bookedAtObj);
				DateTime deliveryAt = orderDetailsDTO.getDeliveryAt();
				String deliveryAtObj = deliveryAt.format("YYYY-MM-DD hh:mm:ss");
				orderDetailsIO.setDeliveryAt(deliveryAtObj);
				orderDetailsIO.setStatusId(orderDetailsDTO.getStatusId());
				orderDetailsIO.setTransactionAmount(orderDetailsDTO.getTransactionAmount());
				orderDetailsIO.setActiveFlag(orderDetailsDTO.getActiveFlag());
				orderDetailsIo.add(orderDetailsIO);
			}
		}
		return orderDetailsIo;
	}
	
	@RequestMapping(value="/{code}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public OrderDetailsIO getOrderHistoryByCode(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		OrderDetailsDTO orderDetailsDto = new OrderDetailsDTO();
		orderDetailsDto.setCode(code);
		OrderDetailsIO orderDerailsIo = new OrderDetailsIO();
		if (authDTO != null) {
			orderDetailsDto = orderDetailService.getOrderHistoryByCode(authDTO, orderDetailsDto);
			orderDerailsIo.setCode(orderDetailsDto.getCode());
			String customer = orderDetailsDto.getCustomer().getCode();
			CustomerIO customerIO = new CustomerIO();
			customerIO.setCode(customer);
			orderDerailsIo.setCustomer(customerIO);
			orderDerailsIo.setFood(orderDetailsDto.getFood());
			orderDerailsIo.setQuantity(orderDetailsDto.getQuantity());
			DateTime bookedAt = orderDetailsDto.getBookedAt();
			String bookedAtObj = bookedAt.format("YYYY-MM-DD hh:mm:ss");
			orderDerailsIo.setBookedAt(bookedAtObj);
			DateTime deliveryAt = orderDetailsDto.getDeliveryAt();
			String deliveryAtObj = deliveryAt.format("YYYY-MM-DD hh:mm:ss");
			orderDerailsIo.setDeliveryAt(deliveryAtObj);
			orderDerailsIo.setStatusId(orderDetailsDto.getStatusId());
			orderDerailsIo.setTransactionAmount(orderDetailsDto.getTransactionAmount());
			orderDerailsIo.setActiveFlag(orderDetailsDto.getActiveFlag());
		}
		return orderDerailsIo;
	}
}