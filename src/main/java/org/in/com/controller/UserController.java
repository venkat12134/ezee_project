package org.in.com.controller;


import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;
import org.in.com.io.UserIO;
import org.in.com.service.AuthService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/{authtoken}")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthService authService;
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO updateUser(@PathVariable("authtoken") String authtoken, @RequestBody UserIO userIO) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		UserIO userIo = new UserIO();
		if (authDTO != null) {
			UserDTO userObj = new UserDTO();
			userObj.setCode(userIO.getCode());
			userObj.setUserName(userIO.getUserName());
			userObj.setPassword(userIO.getPassword());
			userObj.setFirstName(userIO.getFirstName());
			userObj.setLastName(userIO.getLastName());
			userObj.setMobileNumber(userIO.getMobileNumber());
			userObj.setEmailId(userIO.getEmailId());
			userObj.setActiveFlag(userIO.getActiveFlag());
			UserDTO userdto = userService.updateUser(authDTO, userObj);
			userIo.setActiveFlag(userdto.getActiveFlag());
			userIo.setCode(userdto.getCode());
		}
		return userIo;
	}
	
	@RequestMapping(value = "/history", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
	public List<UserIO> getUserHistory(@PathVariable("authtoken") String authtoken) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		List<UserIO> userIO = new ArrayList<UserIO>();
		if (authDTO != null) {
			List<UserDTO> userDto = userService.getUserHistory(authDTO);
			for (UserDTO userObj : userDto) {
				UserIO userIo = new UserIO();
				userIo.setCode(userObj.getCode());
				userIo.setUserName(userObj.getUserName());
				userIo.setPassword(userObj.getPassword());
				userIo.setFirstName(userObj.getFirstName());
				userIo.setLastName(userObj.getLastName());
				userIo.setMobileNumber(userObj.getMobileNumber());
				userIo.setEmailId(userObj.getEmailId());
				userIo.setActiveFlag(userObj.getActiveFlag());
				userIO.add(userIo);
			}
		}
		return userIO;
	}
	
	@RequestMapping(value="/{code}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public UserIO getUserByCode(@PathVariable("authtoken") String authtoken,@PathVariable String code) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		UserDTO userDto = new UserDTO();
		userDto.setCode(code);
		UserIO userIo = new UserIO();
		if (authDTO != null) {
			userDto = userService.getUserByCode(authDTO, userDto);
			userIo.setCode(userDto.getCode());
			userIo.setUserName(userDto.getUserName());
			userIo.setPassword(userDto.getPassword());
			userIo.setFirstName(userDto.getFirstName());
			userIo.setLastName(userDto.getLastName());
			userIo.setMobileNumber(userDto.getMobileNumber());
			userIo.setEmailId(userDto.getEmailId());
			userIo.setActiveFlag(userDto.getActiveFlag());
		}
		return userIo;
	}
	
}