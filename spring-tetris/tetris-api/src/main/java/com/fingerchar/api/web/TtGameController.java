package com.fingerchar.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.service.TtGameService;
import com.fingerchar.core.base.controller.BaseController;
import com.fingerchar.core.util.ResponseUtil;

@RestController
@RequestMapping(SysConfConstant.URL_PREFIX + "/game")
public class TtGameController extends BaseController {

	@Autowired
	TtGameService gameService;

	@PostMapping("start")
	public Object start() throws Exception {
		String userAddress = (String) request.getAttribute("userAddress");
    	if(StringUtils.isEmpty(userAddress)) {
    		return ResponseUtil.unlogin();
    	}
		return this.gameService.start(userAddress);
	}
	
	@PostMapping("finish")
	public Object finish(Long score) throws Exception {
		String userAddress = (String) request.getAttribute("userAddress");
    	if(StringUtils.isEmpty(userAddress)) {
    		return ResponseUtil.unlogin();
    	}
    	if(null == score) {
    		return ResponseUtil.fail(-1, "score is empty");
	    }
		return this.gameService.finish(userAddress, score);
	}

	@PostMapping("detail")
	public Object detail() throws Exception {
		return this.gameService.detail();
	}
}
