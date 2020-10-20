package com.lixuan.statistics.schedule;

import com.lixuan.statistics.service.DailyService;
import com.lixuan.statistics.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private DailyService service;

    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        String day= DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        service.createStatisticsByDay(day);
    }
}
