package com.fingerchar.api.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fingerchar.db.domain.TtSystem;

public class SystemConfCache {

	public static Map<String, String> cache = new HashMap<>();
	
	public static void init(List<TtSystem> configList) {
		if(null != configList && !configList.isEmpty()) {
			configList.stream().forEach(config-> {				
				cache.put(config.getKeyName(), config.getKeyValue());
			});
		}
	}
	
}
