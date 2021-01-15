package com.gdiot.scheduled;

import com.gdiot.service.AsyncService;
import com.gdiot.task.DataSenderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Component
@Slf4j
public class TimeTask {

    @Autowired
    private AsyncService asyncService;

    /**
     * 定时任务每天凌晨1点执行一次，查询所有的用户信息
     * 1，获取所有部门ID及子部门ID
     * 2，获取部门下的用户ID列表
     * 3，根据用户ID获取用户详情
     * 4，用户详情解析，保存数据库
     */
//	@Scheduled(cron = "0 0 0 * * ?")//每天凌晨执行一次
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
    public void scheduledTask() {
        log.info("定时任务每天凌晨1点执行一次，查询所有的用户信息");
        String depId = "1";
        DataSenderTask task = new DataSenderTask(depId, "all_dept_user_detail");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);
    }

    /**
     * 定时任务每天凌晨2点执行一次,查询所有的部门信息
     * 查询所有的部门信息，保存数据库
     */
//	@Scheduled(cron = "0 0 0 * * ?")//每天凌晨执行一次
//	@Scheduled(cron = "0 0 2 * * ?")//每天凌晨2点执行一次
    public void scheduledTask2() {
        log.info("定时任务每天凌晨2点执行一次,查询所有的部门信息，保存数据库");
        String depId = "1";
        DataSenderTask task = new DataSenderTask(depId, "all_dept_detail");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);
    }

    /**
     * 定时任务每天凌晨3点执行一次
     * 查询用户的开票申请单，保存数据库
     */
//	@Scheduled(cron = "0 0 3 * * ?")//每天凌晨3点执行一次
//	@Scheduled(cron = "0 0 3 ? * MON")//每周一凌晨3点执行一次
//  @Scheduled(cron = "0 0 17 ? * FRI")//周五晚上17点分执行一次
    public void scheduledTask3() {
        log.info("定时任务每周一凌晨3点执行一次，查询用户的申请单，保存数据库");
        //默认获取最近一天的开票申请，当前时间的前24小时
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 7 * 24 * 60 * 60 * 1000;
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        DataSenderTask task = new DataSenderTask(map, "all_process_detail");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);
    }

    @Scheduled(cron = "0 0 0/1 * * ?")//每个整点执行一次：
    public void testTask() {

        log.info("定时任务每小时执行一次");
    }

    // @Scheduled(cron = "30 0/10 * * * ?") //10分钟一次
    public void testTask10() {

        log.info("定时任务每10分钟执行一次");
    }
}
