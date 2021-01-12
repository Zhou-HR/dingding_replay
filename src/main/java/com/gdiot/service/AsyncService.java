package com.gdiot.service;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public interface AsyncService {
	/**
	 *
	 * @param runnable
	 */
	void executeAsync(Runnable runnable );

	/**
	 *
	 * @param runnable
	 */
	void executeMqttAsync(Runnable runnable );
}
