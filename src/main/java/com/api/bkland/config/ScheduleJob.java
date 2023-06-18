package com.api.bkland.config;

import com.api.bkland.service.NotifyService;
import com.api.bkland.service.RealEstatePostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJob {
    private Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

    @Autowired
    private RealEstatePostService realEstatePostService;

    @Autowired
    private NotifyService notifyService;

    @Scheduled(cron = "59 59 23 * * *", zone = "GMT+7:00")
    public void disablePostExpire() {
//        logger.info("crontab work");
        realEstatePostService.disablePostExpire();
    }

    @Scheduled(cron = "59 59 23 * * *", zone = "GMT+7:00")
    public void notifyToSpecialAccount() {
        notifyService.thongBaoChuaDongTien();
        notifyService.thongBaoDenHanDongTien();
    }
}
