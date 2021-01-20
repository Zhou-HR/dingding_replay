package com.gdiot.service;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface AsyncService {

    /**
     * 执行异步线程
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:40
     * @param runnable
     * @return void
     */
    void executeAsync(Runnable runnable);
}
