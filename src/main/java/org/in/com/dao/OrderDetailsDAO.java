package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.OrderDetailsDTO;
import org.in.com.dto.enumeration.OrderStatusId;
import org.springframework.stereotype.Repository;

import hirondelle.date4j.DateTime;
import lombok.Cleanup;

@Repository
public class OrderDetailsDAO {

	public OrderDetailsDTO updateOrderDetails(AuthDTO authDTO, OrderDetailsDTO orderDetailsDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int index = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_ORDER_DETAILS_IUD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setString(++index, orderDetailsDTO.getCode());
			callableStatement.setInt(++index, authDTO.getNamespace().getId());
			callableStatement.setInt(++index, orderDetailsDTO.getCustomer().getId());
			callableStatement.setString(++index, orderDetailsDTO.getFood());
			callableStatement.setInt(++index, orderDetailsDTO.getQuantity());
			DateTime bookedAt = orderDetailsDTO.getBookedAt();
			String bookedAtObj = bookedAt.format("YYYY-MM-DD hh:mm:ss");
			callableStatement.setString(++index, bookedAtObj);
			DateTime deliveryAt = orderDetailsDTO.getDeliveryAt();
			String deliveryAtObj = deliveryAt.format("YYYY-MM-DD hh:mm:ss");
			callableStatement.setString(++index, deliveryAtObj);
			callableStatement.setString(++index, orderDetailsDTO.getStatusId().name());
			callableStatement.setInt(++index, orderDetailsDTO.getTransactionAmount());
			callableStatement.setInt(++index, orderDetailsDTO.getActiveFlag());
			callableStatement.setInt(++index, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return orderDetailsDTO;
	}
	
	public List<OrderDetailsDTO> getOrderHistry(AuthDTO authDTO,String fromDate, String toDate){
		List<OrderDetailsDTO> orderDetailsDTO = new ArrayList<>();
		try{
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
            String sql ="SELECT code, customer_id, food, quantity, booked_at, delivery_at, status_id, transaction_amount, active_flag FROM order_details WHERE namespace_id = ? AND active_flag < 2";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authDTO.getNamespace().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
            	OrderDetailsDTO orderDetailsObj = new OrderDetailsDTO();
            	orderDetailsObj.setCode(resultSet.getString("code"));
            	int customerId = resultSet.getInt("customer_id");
            	CustomerDTO customer = new CustomerDTO();
            	customer.setId(customerId);
            	orderDetailsObj.setCustomer(customer);
            	orderDetailsObj.setFood(resultSet.getString("food"));
            	orderDetailsObj.setQuantity(resultSet.getInt("quantity"));
            	String bookedAt = resultSet.getString("booked_at");
            	DateTime bookedAtObj = new DateTime(bookedAt);
            	orderDetailsObj.setBookedAt(bookedAtObj);
            	String deliveryAt = resultSet.getString("delivery_at");
            	DateTime deliveryAtObj =new DateTime (deliveryAt);
            	orderDetailsObj.setDeliveryAt(deliveryAtObj);
            	orderDetailsObj.setStatusId(OrderStatusId.valueOf(resultSet.getString("status_id")));
            	orderDetailsObj.setTransactionAmount(resultSet.getInt("transaction_amount"));
            	orderDetailsObj.setActiveFlag(resultSet.getInt("active_flag"));
                orderDetailsDTO.add(orderDetailsObj);            	
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderDetailsDTO;
	}
	
	public OrderDetailsDTO getOrderHistoryByCode(AuthDTO authDTO,OrderDetailsDTO orderDetails){
           OrderDetailsDTO orderDetailsObj = new OrderDetailsDTO();
		try{
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
            String sql ="SELECT code, customer_id, food, quantity, booked_at, delivery_at, status_id, transaction_amount, active_flag FROM order_details WHERE namespace_id = ? AND code = ? AND active_flag < 2";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authDTO.getNamespace().getId());
            preparedStatement.setString(2, orderDetails.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
            	orderDetailsObj.setCode(resultSet.getString("code"));
            	int customerId = resultSet.getInt("customer_id");
            	CustomerDTO customer = new CustomerDTO();
            	customer.setId(customerId);
            	orderDetailsObj.setCustomer(customer);
            	orderDetailsObj.setFood(resultSet.getString("food"));
            	orderDetailsObj.setQuantity(resultSet.getInt("quantity"));
            	String bookedAt = resultSet.getString("booked_at");
            	DateTime bookedAtObj = new DateTime(bookedAt);
            	orderDetailsObj.setBookedAt(bookedAtObj);
            	String deliveryAt = resultSet.getString("delivery_at");
            	DateTime deliveryAtObj =new DateTime (deliveryAt);
            	orderDetailsObj.setDeliveryAt(deliveryAtObj);
            	orderDetailsObj.setStatusId(OrderStatusId.valueOf(resultSet.getString("status_id")));
            	orderDetailsObj.setTransactionAmount(resultSet.getInt("transaction_amount"));
            	orderDetailsObj.setActiveFlag(resultSet.getInt("active_flag"));
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderDetailsObj;
	}
}
