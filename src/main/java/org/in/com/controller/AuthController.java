package org.in.com.controller;

import org.in.com.dto.AuthDTO;
import org.in.com.exception.InvalidException;
import org.in.com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authtoken")
public class AuthController {
	
	@Autowired
	AuthService authService;

	
	@RequestMapping(value="/authcreate/{username}/{password}/{namespacecode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthDTO authCreate(@PathVariable String username, @PathVariable String password, @PathVariable("namespacecode") String namespaceCode) throws InvalidException {

		AuthDTO authObj = new AuthDTO();

		authObj = authService.authCreate(username, password, namespaceCode);

		return authObj;
	}
	

	@RequestMapping(value="/getuser/{authtoken}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthDTO getUser(@PathVariable String authtoken) throws Exception {
		AuthDTO authObj = new AuthDTO();

		authObj = authService.getUserByToken(authtoken);

		return authObj;
	}
	
}