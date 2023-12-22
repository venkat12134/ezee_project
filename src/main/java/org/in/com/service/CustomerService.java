package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;

public interface CustomerService {

	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerObj);

	public CustomerDTO getCustomer(CustomerDTO customer, AuthDTO authDTO);

	public CustomerDTO getCustomerByCode(CustomerDTO customer, AuthDTO authDTO);

	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO);

	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customerDTO);

	public CustomerDTO getCustomerById(CustomerDTO customer, AuthDTO authDTO);

}
