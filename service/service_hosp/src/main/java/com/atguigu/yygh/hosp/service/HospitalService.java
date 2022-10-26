package com.atguigu.yygh.hosp.service;


import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/25
 */
public interface HospitalService {
    void save(Map<String, Object> stringObjectMap);


    Hospital readByHoscode(String hoscode);
}
