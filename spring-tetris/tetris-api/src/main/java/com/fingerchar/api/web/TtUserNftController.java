package com.fingerchar.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.service.TtUserNftService;
import com.fingerchar.core.base.controller.BaseController;
import com.fingerchar.core.util.ResponseUtil;

@RestController
@RequestMapping(SysConfConstant.URL_PREFIX + "/nft")
public class TtUserNftController extends BaseController {
	
	@Autowired
	TtUserNftService nftService;

	@PostMapping("list")
	public Object list() throws Exception {
		String userAddress = (String) request.getAttribute("userAddress");
    	if(StringUtils.isEmpty(userAddress)) {
    		return ResponseUtil.unlogin();
    	}
		return this.nftService.list(userAddress, this.getPageInfo());
	}
	
	@PostMapping("use")
	public Object use(String tokenId) {
		String userAddress = (String) request.getAttribute("userAddress");
    	if(StringUtils.isEmpty(userAddress)) {
    		return ResponseUtil.unlogin();
    	}
		return this.nftService.use(userAddress, tokenId);
	}
}
