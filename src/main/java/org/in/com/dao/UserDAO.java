package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
//import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class UserDAO {

	public UserDTO updateUser(AuthDTO authDTO, UserDTO userDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int pindex = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_USER_IUD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setString(++pindex, userDTO.getCode());
			callableStatement.setInt(++pindex, authDTO.getNamespace().getId());
			callableStatement.setString(++pindex, userDTO.getUserName());
			callableStatement.setString(++pindex, userDTO.getPassword());
			callableStatement.setString(++pindex, userDTO.getFirstName());
			callableStatement.setString(++pindex, userDTO.getLastName());
			callableStatement.setLong(++pindex, userDTO.getMobileNumber());
			callableStatement.setString(++pindex, userDTO.getEmailId());
			callableStatement.setInt(++pindex, userDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDTO;
	}

	public List<UserDTO> getUserHistory(AuthDTO authDTO) {
		List<UserDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, username, password, first_name, last_name, mobile_number, email_id, active_flag FROM user WHERE namespace_id = ? AND active_flag < 2";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserDTO userDto = new UserDTO();
				userDto.setCode(resultSet.getString("code"));
				userDto.setUserName(resultSet.getString("username"));
				userDto.setPassword(resultSet.getString("password"));
				userDto.setFirstName(resultSet.getString("first_name"));
				userDto.setLastName(resultSet.getString("last_name"));
				userDto.setMobileNumber(resultSet.getLong("mobile_number"));
				userDto.setEmailId(resultSet.getString("email_id"));
				userDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(userDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public UserDTO getUserByCode(AuthDTO authDTO, UserDTO user) {

		UserDTO userDto = new UserDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, username, password, first_name, last_name, mobile_number, email_id, active_flag FROM user WHERE namespace_id = ? AND code = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			preparedStatement.setString(2, user.getCode());
			ResultSet resultSet = preparedStatement.executeQuery();
			{
				if (resultSet.next()) {
					userDto.setCode(resultSet.getString("code"));
					userDto.setUserName(resultSet.getString("username"));
					userDto.setPassword(resultSet.getString("password"));
					userDto.setFirstName(resultSet.getString("first_name"));
					userDto.setLastName(resultSet.getString("last_name"));
					userDto.setMobileNumber(resultSet.getLong("mobile_number"));
					userDto.setEmailId(resultSet.getString("email_id"));
					userDto.setActiveFlag(resultSet.getInt("active_flag"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;

	}

	public UserDTO getUser(AuthDTO authDTO, UserDTO userLogin) {
		UserDTO userDto = new UserDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT username, password, namespace_id, first_name, last_name, email_id, mobile_number, active_flag FROM user WHERE username = ? AND namespace_id = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userLogin.getUserName());
			preparedStatement.setInt(2, authDTO.getNamespace().getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				userDto.setUserName(resultSet.getString("username"));
				userDto.setPassword(resultSet.getString("password"));
				int id = resultSet.getInt("namespace_id");
				NamespaceDTO namespaceId = new NamespaceDTO();
				namespaceId.setId(id);
				userDto.setNamespace(namespaceId);
				userDto.setFirstName(resultSet.getString("first_name"));
				userDto.setLastName(resultSet.getString("last_name"));
				userDto.setEmailId(resultSet.getString("email_id"));
				userDto.setMobileNumber(resultSet.getLong("mobile_number"));
				userDto.setActiveFlag(resultSet.getInt("active_flag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}

	public AuthDTO authCreate(AuthDTO authDTO, UserDTO userLogin) {
		UserDTO userObj = new UserDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "UPDATE user SET auth_token = ? WHERE namespace_id = ? AND username = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, authDTO.getAuthtoken());
			preparedStatement.setInt(2, authDTO.getNamespace().getId());
			preparedStatement.setString(3, userLogin.getUserName());
			int rows = preparedStatement.executeUpdate();

			if (rows > 0) {
				authDTO.setUser(userObj);
			} else {
				System.out.println("No user found or no update performed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return authDTO;
	}

	public UserDTO getUserByToken(AuthDTO authDTO, String authtoken) {
		UserDTO userDto = new UserDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, namespace_id, username, password, auth_token, first_name, last_name, mobile_number, email_id, active_flag FROM user WHERE auth_token = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, authtoken);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				userDto.setId(resultSet.getInt("id"));
				int id = resultSet.getInt("namespace_id");
				NamespaceDTO namespace = new NamespaceDTO();
				namespace.setId(id);
				userDto.setNamespace(namespace);
				userDto.setUserName(resultSet.getString("username"));
				userDto.setPassword(resultSet.getString("password"));
				userDto.setAuthtoken(resultSet.getString("auth_token"));
				userDto.setFirstName(resultSet.getString("first_name"));
				userDto.setLastName(resultSet.getString("last_name"));
				userDto.setMobileNumber(resultSet.getLong("mobile_number"));
				userDto.setEmailId(resultSet.getString("email_id"));
				userDto.setActiveFlag(resultSet.getInt("active_flag"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
}