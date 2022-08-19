package com.fingerchar.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.db.domain.TtSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TtSystemConfigService {

	@Autowired
	IBaseService baseService;
	

	public String get(String key) {
		if(StringUtils.isEmpty(key)) {
			return null;
		}
		
		QueryWrapper<TtSystem> wrapper = new QueryWrapper<>();
		wrapper.eq(TtSystem.KEY_NAME, key);
		TtSystem system = this.baseService.getByCondition(TtSystem.class, wrapper);
		if(null == system) {
			return null;
		} else {
			return system.getKeyValue();
		}
	}
	
	public boolean set(String key, String value) {
		if(StringUtils.isEmpty(key)) {
			return false;
		}
		UpdateWrapper<TtSystem> wrapper = new UpdateWrapper<>();
		wrapper.set(TtSystem.KEY_VALUE, value);
		wrapper.eq(TtSystem.KEY_NAME, key);
		return this.baseService.updateByCondition(TtSystem.class, wrapper) > 0;
	}

	/**
	 * @return
	 */
	public List<TtSystem> listAll() {
		QueryWrapper<TtSystem> wrapper = new QueryWrapper<>();
		wrapper.eq(TtSystem.DELETED, false);
		return this.baseService.findByCondition(TtSystem.class, wrapper);
	}
}
