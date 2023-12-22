package org.in.com.impl;

import java.util.List;

import org.in.com.dao.NamespaceDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamespaceServiceImpl implements NamespaceService {

	@Autowired
	private NamespaceDAO namespaceDAO;

	@Override
	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) {
		try {
			namespaceDTO = namespaceDAO.updateNamespace(authDTO, namespaceDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namespaceDTO;
	}

	@Override
	public List<NamespaceDTO> getNamespaceHistory(AuthDTO authDTO) {
		List<NamespaceDTO> namespaceDto = null;
		try {
			namespaceDto = namespaceDAO.getNamespaceHistory(authDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namespaceDto;
	}

	@Override
	public NamespaceDTO getNamespace(AuthDTO authDTO, NamespaceDTO namespace) {

		try {
			namespace = namespaceDAO.getNamespace(authDTO, namespace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return namespace;

	}

}
