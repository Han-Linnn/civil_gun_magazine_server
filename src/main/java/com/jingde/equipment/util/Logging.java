package com.jingde.equipment.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by oceanover on 2019-03-22.
 */
public class Logging {
    /**
     * 记录info级别日志到日志文件
     * 配合logback.xml配置文件
     */
    public static void info(Object data) {
        Logger logger = LoggerFactory.getLogger("server-logger");
        if (data.getClass().equals(String.class)) {
            logger.info((String) data);
        } else {
            logger.info(JSON.toJSONString(data));
        }
    }
}
