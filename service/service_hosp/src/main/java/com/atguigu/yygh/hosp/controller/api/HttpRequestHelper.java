package com.atguigu.yygh.hosp.controller.api;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/25
 */
public class HttpRequestHelper {
    public static Map<String, Object> switchMap(Map<String, String[]> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            resultMap.put(param.getKey(), param.getValue()[0]);
        }
        return resultMap;
    }
}