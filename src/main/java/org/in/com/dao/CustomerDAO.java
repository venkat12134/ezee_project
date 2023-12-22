package org.in.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.enumeration.CustomerRatingType;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class CustomerDAO {

	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int pindex = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_CUSTOMER_IUD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setString(++pindex, customerDTO.getCode());
			callableStatement.setInt(++pindex, authDTO.getNamespace().getId());
			callableStatement.setString(++pindex, customerDTO.getAddress());
			callableStatement.setString(++pindex, customerDTO.getFirstName());
			callableStatement.setString(++pindex, customerDTO.getLastName());
			callableStatement.setLong(++pindex, customerDTO.getMobileNumber());
			callableStatement.setString(++pindex, customerDTO.getEmailId());
			callableStatement.setString(++pindex, customerDTO.getCustomerRatingType().name());
			callableStatement.setInt(++pindex, customerDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDTO;
	}
	
	public CustomerDTO getCustomer(AuthDTO authDTO, CustomerDTO customerDTO){
		try{
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, address, first_name, last_name, mobile_number, email_id, rating_type_id, active_flag FROM customer WHERE namespace_id = ? AND code = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			preparedStatement.setString(2, customerDTO.getCode());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				customerDTO.setId(resultSet.getInt("id"));
				customerDTO.setAddress(resultSet.getString("address"));
				customerDTO.setFirstName(resultSet.getString("first_name"));
				customerDTO.setLastName(resultSet.getString("last_name"));
				customerDTO.setMobileNumber(resultSet.getLong("mobile_number"));
				customerDTO.setEmailId(resultSet.getString("email_id"));
				customerDTO.setCustomerRatingType(CustomerRatingType.valueOf(resultSet.getString("rating_type_id")));
				customerDTO.setActiveFlag(resultSet.getInt("active_flag"));				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return customerDTO;
	}

	public CustomerDTO getCustomerById(AuthDTO authDTO, CustomerDTO customerDTO){
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, address, first_name, last_name, mobile_number, email_id, rating_type_id, active_flag FROM customer WHERE namespace_id = ? AND id = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			preparedStatement.setInt(2, customerDTO.getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				customerDTO.setCode(resultSet.getString("code"));
				customerDTO.setAddress(resultSet.getString("address"));
				customerDTO.setFirstName(resultSet.getString("first_name"));
				customerDTO.setLastName(resultSet.getString("last_name"));
				customerDTO.setMobileNumber(resultSet.getLong("mobile_number"));
				customerDTO.setEmailId(resultSet.getString("email_id"));
				customerDTO.setCustomerRatingType(CustomerRatingType.valueOf(resultSet.getString("rating_type_id")));
				customerDTO.setActiveFlag(resultSet.getInt("active_flag"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDTO;
	}
	
	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customerObj) {
		List<CustomerDTO> customer = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, address, first_name, last_name, mobile_number, email_id, rating_type_id, active_flag FROM customer WHERE namespace_id = ? AND id = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			preparedStatement.setInt(2, customerObj.get(0));
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setCode(resultSet.getString("code"));
				customerDTO.setAddress(resultSet.getString("address"));
				customerDTO.setFirstName(resultSet.getString("first_name"));
				customerDTO.setLastName(resultSet.getString("last_name"));
				customerDTO.setMobileNumber(resultSet.getLong("mobile_number"));
				customerDTO.setEmailId(resultSet.getString("email_id"));
				customerDTO.setCustomerRatingType(CustomerRatingType.valueOf(resultSet.getString("rating_type_id")));
				customerDTO.setActiveFlag(resultSet.getInt("active_flag"));
				customer.add(customerDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}
	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO) {
		List<CustomerDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, address, first_name, last_name, mobile_number, email_id, rating_type_id, active_flag FROM customer WHERE namespace_id = ? AND active_flag < 2";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getUser().getNamespace().getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CustomerDTO customerDto = new CustomerDTO();
				customerDto.setCode(resultSet.getString("code"));
				customerDto.setAddress(resultSet.getString("address"));
				customerDto.setFirstName(resultSet.getString("first_name"));
				customerDto.setLastName(resultSet.getString("last_name"));
				customerDto.setMobileNumber(resultSet.getLong("Mobile_number"));
				customerDto.setEmailId(resultSet.getString("email_id"));
				customerDto.setCustomerRatingType(CustomerRatingType.valueOf(resultSet.getString("rating_type_id")));
				customerDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(customerDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
