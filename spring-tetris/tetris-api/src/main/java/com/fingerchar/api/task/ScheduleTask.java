package com.fingerchar.api.task;

import com.fingerchar.api.service.task.NftTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fingerchar.api.service.task.OrderTaskService;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleTask {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	
	public static Boolean isProcessing = false;
	
	public static Boolean isExpiredProcessing = false;
	
	@Autowired
	OrderTaskService orderTaskService;

	@Autowired
	NftTaskService nftTaskService ;
	
	@Scheduled(cron = "*/6 * * * * ?")
    private void startProcessEvent() {
		// this.orderTaskService.processOrder();
		if(isProcessing.booleanValue()) {
			logger.info("task is in processing status");
			return;
		}
		synchronized (isProcessing) {
			if(isProcessing.booleanValue()) {
				logger.info("task is in processing status");
				return;
			}
			isProcessing = true;
		}
		logger.info("task starting");
		try {
			this.orderTaskService.processOrder();
			this.nftTaskService.processNft();
		} catch (Exception e) {
			logger.error("订单同步异常", e);
		} finally {
			isProcessing = false;
		}
    }

	// @Scheduled(cron = "*/6 * * * * ?")
	/*
	private void startProcessExpired() {
		if(isExpiredProcessing.booleanValue()) {
			logger.info("expired task is in processing status");
			return;
		}
		synchronized (isExpiredProcessing) {
			if(isExpiredProcessing.booleanValue()) {
				logger.info("expired task is in processing status");
				return;
			}
			isExpiredProcessing = true;
		}
		logger.info("expired task starting");
		try {
			this.orderTaskService.processExpiredOrder();
		} catch (Exception e) {
			logger.error("订单状态处理", e);
		} finally {
			isExpiredProcessing = false;
		}
	}
	 */
}
