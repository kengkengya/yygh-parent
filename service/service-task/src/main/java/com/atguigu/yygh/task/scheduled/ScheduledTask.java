package com.atguigu.yygh.task.scheduled;


import com.atguigu.common.rabbit.service.RabbitService;
import com.atguigu.common.rabbit.utils.MqConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private RabbitService rabitService;

    /**
     * 每天8点执行方法，就医提醒
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void task(){
        rabitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"");
    }
}
