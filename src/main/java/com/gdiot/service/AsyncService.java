package com.gdiot.service;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public interface AsyncService {

    /**
     * 执行异步线程
     *
     * @param runnable
     */
    void executeAsync(Runnable runnable);
}
