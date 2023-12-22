package org.in.com.impl;

import org.in.com.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

import org.in.com.dao.UserDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;
import org.in.com.ehcache.EhcacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.ehcache.Element;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserDTO updateUser(AuthDTO authDTO, UserDTO userDTO) {
		try {
			userDTO = userDao.updateUser(authDTO, userDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> getUserHistory(AuthDTO authDTO) {
		List<UserDTO> user = null;
		try {
			user = userDao.getUserHistory(authDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public UserDTO getUserByCode(AuthDTO authDTO, UserDTO user) {
		Element element = EhcacheManager.getUserCache().get(user);
		UserDTO userDTO = null;
		if (element != null) {
			userDTO = (UserDTO) element.getObjectValue();
		} else {
			try {
				userDTO = userDao.getUserByCode(authDTO, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			element = new Element(user, userDTO);
			EhcacheManager.getUserCache().put(element);
		}
		System.out.println("user data from ehcache" + userDTO);
		return userDTO;
	}

	@Override
	public UserDTO getUser(AuthDTO authDTO, UserDTO userLogin) {
		try {
			userLogin = userDao.getUser(authDTO, userLogin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLogin;
	}

	@Override
	public AuthDTO authCreate(AuthDTO authDTO, UserDTO userLogin) {
		try {
			authDTO = userDao.authCreate(authDTO, userLogin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authDTO;
	}

	@Override
	public UserDTO getUserByToken(AuthDTO authDTO, String authtoken) {
		UserDTO user = new UserDTO();
		try {
			user = userDao.getUserByToken(authDTO, authtoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
