package org.in.com.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class NamespaceDAO {
	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int pindex = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_NAMESPACE_IUD(?, ?, ?, ?, ?)}");
			callableStatement.setString(++pindex, namespaceDTO.getCode());
			callableStatement.setString(++pindex, namespaceDTO.getName());
			callableStatement.setString(++pindex, namespaceDTO.getAddress());
			callableStatement.setInt(++pindex, namespaceDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namespaceDTO;
	}
	public List<NamespaceDTO> getNamespaceHistory(AuthDTO authDTO) {
		List<NamespaceDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, name, address, active_flag FROM namespace WHERE active_flag < 2";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				NamespaceDTO namespaceDto = new NamespaceDTO();
				namespaceDto.setCode(resultSet.getString("code"));
				namespaceDto.setName(resultSet.getString("name"));
				namespaceDto.setAddress(resultSet.getString("address"));
				namespaceDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(namespaceDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public NamespaceDTO getNamespace(AuthDTO authDTO, NamespaceDTO namespace) {
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "";
			if (namespace.getId() != 0) {
				sql = "SELECT id, code, name, address, active_flag FROM namespace WHERE id = ? AND active_flag = 1";
			} else {
				sql = "SELECT id, code, name, address, active_flag FROM namespace WHERE code = ? AND active_flag = 1";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			if (namespace.getId() != 0) {
				preparedStatement.setInt(1, namespace.getId());
			} else {
				preparedStatement.setString(1, namespace.getCode());
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				namespaceDTO.setId(resultSet.getInt("id"));
				namespaceDTO.setCode(resultSet.getString("code"));
				namespaceDTO.setName(resultSet.getString("name"));
				namespaceDTO.setAddress(resultSet.getString("address"));
				namespaceDTO.setActiveFlag(resultSet.getInt("active_flag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namespaceDTO;
	}
}
