package org.in.com.impl;

import java.util.List;

import org.in.com.dao.CustomerDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.rediscache.CustomerCacheManager;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDao;

	@Autowired
	private CustomerCacheManager customerCacheManager;

	@Override
	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerObj) {
		try {
			customerObj = customerDao.updateCustomer(authDTO, customerObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerObj;
	}

	@Override
	public CustomerDTO getCustomer(CustomerDTO customer, AuthDTO authDTO) {
		try {
			customer = customerDao.getCustomer(authDTO, customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public CustomerDTO getCustomerById(CustomerDTO customer, AuthDTO authDTO) {
		try {
			customer = customerDao.getCustomerById(authDTO, customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customerDTO) {
		List<CustomerDTO> customer = null;
		try {
			customer = customerDao.getCustomerByIdList(authDTO, customerDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customer;
	}

	@Override
	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO) {
		List<CustomerDTO> customer = null;
		try {
			customer = customerDao.getCustomerHistory(authDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public CustomerDTO getCustomerByCode(CustomerDTO customer, AuthDTO authDTO) {

		CustomerDTO customerDTO = customerCacheManager.getCustomerData(customer);
		if (customerDTO == null) {
			try {
				customerDTO = customerDao.getCustomer(authDTO, customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
			customerCacheManager.putCustomerData(customer, customerDTO);
		}
		System.out.println("data stored redis" + customerDTO);
		return customerDTO;
	}
}
