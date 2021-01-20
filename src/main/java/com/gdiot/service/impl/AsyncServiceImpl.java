package com.gdiot.service.impl;

import com.gdiot.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Service(value = "AsyncService")
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync(Runnable runnable) {
        // 启动线程
        runnable.run();
    }
}
