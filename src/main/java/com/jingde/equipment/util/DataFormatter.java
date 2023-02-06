package com.jingde.equipment.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oceanover on 2019-03-29.
 */
public class DataFormatter {
    // javaBean -> Map 将字符串字段转数组
    public static <T> Map formatData(T data, List<String> keys, String splitString) throws Exception {
        BeanMap beanMap = BeanMap.create(data);
        Map<String, Object> res = new HashMap<>(beanMap);
        for (String key : keys) {
            String value = (String) res.get(key);
            if (value != null) {
                String[] array = value.split(splitString);
                res.put(key, array);
            }
        }
        return res;
    }
}
