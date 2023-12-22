package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;

public interface NamespaceService {

	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceObj);

	public List<NamespaceDTO> getNamespaceHistory(AuthDTO authDTO);

	public NamespaceDTO getNamespace(AuthDTO authDTO, NamespaceDTO namespace);

}
