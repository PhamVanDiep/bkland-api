package com.api.bkland.config;

import com.api.bkland.service.RealEstatePostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJob {
    private Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

//    @Scheduled(fixedRate = 2000)
//    public void scheduleTest() {
//        logger.info("Schedule work.");
//    }
    @Autowired
    private RealEstatePostService realEstatePostService;

    @Scheduled(cron = "59 59 23 * * *", zone = "GMT+7:00")
    public void disablePostExpire() {
//        logger.info("crontab work");
        realEstatePostService.disablePostExpire();
    }
}
