package org.in.com.impl;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.exception.InvalidException;
import org.in.com.impl.randomstring.RandomString;
import org.in.com.service.AuthService;
import org.in.com.service.NamespaceService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private NamespaceService namespaceService;

	@Override
	public AuthDTO getUserByToken(String authtoken) {
		AuthDTO authDto = new AuthDTO();
		try {
			UserDTO user = userService.getUserByToken(authDto, authtoken);
			if (user != null && authtoken.equals(user.getAuthtoken())) {
				authDto.setUser(user);
				NamespaceDTO namespace = namespaceService.getNamespace(authDto, user.getNamespace());
				authDto.setNamespace(namespace);
			} else {
				throw new InvalidException("invalid Authtoken");
			}

		} catch (InvalidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authDto;
	}

	@Override
	public AuthDTO authCreate(String username, String password, String namespaceCode) {
		AuthDTO authDto = new AuthDTO();
		try {
			UserDTO userLogin = new UserDTO();
			NamespaceDTO namespace = new NamespaceDTO();
			namespace.setCode(namespaceCode);
			userLogin.setUserName(username);
			userLogin.setPassword(password);
			namespace = namespaceService.getNamespace(authDto, namespace);
			authDto.setNamespace(namespace);
			UserDTO user = userService.getUser(authDto, userLogin);
			if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
				String token = RandomString.generateRandomString(20);
				authDto.setAuthtoken(token);
				authDto.setUser(user);
				userService.authCreate(authDto, userLogin);
			} else {
				throw new InvalidException("Invalid username and password");
			}

		} catch (InvalidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return authDto;
	}

}