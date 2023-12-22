package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.io.NamespaceIO;
import org.in.com.service.AuthService;
import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/namespace/{authtoken}")
public class NamespaceController {

	@Autowired
	NamespaceService namespaceService;
	
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO updateNamespace(@PathVariable("authtoken") String authtoken,@RequestBody NamespaceIO namespaceIO) throws Exception {
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		NamespaceIO namespaceIo = new NamespaceIO();
		if (authDTO != null) {
			NamespaceDTO namespaceObj = new NamespaceDTO();
			namespaceObj.setCode(namespaceIO.getCode());
			namespaceObj.setName(namespaceIO.getName());
			namespaceObj.setAddress(namespaceIO.getAddress());
			namespaceObj.setActiveFlag(namespaceIO.getActiveFlag());
			NamespaceDTO namespaceDTO = namespaceService.updateNamespace(authDTO, namespaceObj);
			namespaceIo.setActiveFlag(namespaceDTO.getActiveFlag());
			namespaceIo.setCode(namespaceDTO.getCode());
		}
		return namespaceIo;
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<NamespaceIO> getNamespaceHistory(@PathVariable("authtoken") String authtoken) throws Exception {
		List<NamespaceIO> namespacesIO = new ArrayList<NamespaceIO>();
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		if (authDTO != null) {
			List<NamespaceDTO> namespacesDtoList = namespaceService.getNamespaceHistory(authDTO);
			for (NamespaceDTO namespaceDto : namespacesDtoList) {
				NamespaceIO namespaceObj = new NamespaceIO();
				namespaceObj.setCode(namespaceDto.getCode());
				namespaceObj.setName(namespaceDto.getName());
				namespaceObj.setAddress(namespaceDto.getAddress());
				namespaceObj.setActiveFlag(namespaceDto.getActiveFlag());
				namespacesIO.add(namespaceObj);
			}
		}
		return namespacesIO;
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO getNamespace(@PathVariable("authtoken") String authtoken,@PathVariable String code) throws Exception {
		NamespaceIO namespaceIo = new NamespaceIO();
		AuthDTO authDTO = new AuthDTO();
		authDTO = authService.getUserByToken(authtoken);
		if (authDTO != null) {
			NamespaceDTO namespaceDto = new NamespaceDTO();
			namespaceDto.setCode(code);
			namespaceDto = namespaceService.getNamespace(authDTO, namespaceDto);
			namespaceIo.setCode(namespaceDto.getCode());
			namespaceIo.setName(namespaceDto.getName());
			namespaceIo.setAddress(namespaceDto.getAddress());
			namespaceIo.setActiveFlag(namespaceDto.getActiveFlag());
		}
		return namespaceIo;
	}
}