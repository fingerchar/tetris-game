package com.fingerchar.api.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.service.TtSystemConfigService;
import com.fingerchar.db.domain.TtSystem;

@Component
public class InitRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(InitRunner.class);
	
	@Autowired
	TtSystemConfigService configService;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("开始初始化数据");
		this.initCache();
		logger.info("初始化完成...");
	}
	
	private void initCache() {
		List<TtSystem> configList = this.configService.listAll();
		logger.info("初始化系统参数");
		SystemConfCache.init(configList);
		logger.info("初始化系统参数完成");
	}
}
