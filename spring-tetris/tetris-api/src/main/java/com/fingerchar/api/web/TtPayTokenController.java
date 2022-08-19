package com.fingerchar.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.service.TtPayTokenService;
import com.fingerchar.core.base.controller.BaseController;

@RestController
@RequestMapping(SysConfConstant.URL_PREFIX + "/paytoken")
public class TtPayTokenController extends BaseController {
	
	@Autowired
	TtPayTokenService payTokenService;
	@PostMapping("list")
	public Object list() throws Exception {
		return this.payTokenService.list();
	}
}
