package com.gdiot.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@RestController
@RequestMapping("/dd")
public class DingDingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingController.class);
}
