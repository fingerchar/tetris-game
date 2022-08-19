package com.fingerchar.api.web;

import com.fingerchar.db.domain.TtUserBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.dto.ProductOrderDto;
import com.fingerchar.api.service.TtPayTokenService;
import com.fingerchar.api.service.TtPayTokenTxService;
import com.fingerchar.api.service.TtUserService;
import com.fingerchar.core.base.controller.BaseController;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.ResponseUtil;

@RestController
@RequestMapping(SysConfConstant.URL_PREFIX + "/user")
public class TtUserController extends BaseController {
	
    @Autowired
    TtUserService userService;

    @Autowired
    TtPayTokenService payTokenService;

    @Autowired
    TtPayTokenTxService payTokenTxService;

    @Autowired
    IBaseService baseService;

    @PostMapping("getbalance")
    public Object getBalance(String token, String userAddress, Long timestamp, String sign) {
    	return this.userService.getBalance(token, userAddress, timestamp, sign);
    }

    @PostMapping("withdraw")
    public Object withdraw(ProductOrderDto order, String sign) {
    	try {
    		return this.userService.withdraw(order, sign);
    	} catch (Exception e) {
    		e.printStackTrace();
			return ResponseUtil.fail(-1, "签名异常！");
		}
    }
    
    @PostMapping("login")
    public Object login(String token, String code) {
    	return this.userService.login(code);
    }
    
    @PostMapping("reload")
    public Object reload() {
	    String userAddress = (String) request.getAttribute("userAddress");
	    if(StringUtils.isEmpty(userAddress)) {
		    return ResponseUtil.unlogin();
	    }
    	return this.userService.reload(userAddress);
    }
    
    @PostMapping("balance")
    public Object balance(String token) {
    	String userAddress = (String) request.getAttribute("userAddress");
    	if(StringUtils.isEmpty(userAddress)) {
    		return ResponseUtil.unlogin();
    	}
    	return ResponseUtil.ok(this.userService.balance(token, userAddress));
    }
}
