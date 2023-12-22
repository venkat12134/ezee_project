package org.in.com.service;

import org.in.com.dto.AuthDTO;

public interface AuthService {

	public AuthDTO authCreate(String username, String password, String namespaceCode);

	public AuthDTO getUserByToken(String authtoken);
}
