package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.mapper.HospitalMapper;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.attguigu.abcommon.config.YyghException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/25
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Override
    public void save(Map<String, Object> stringObjectMap) {
        //1 把paramMap变成对象,使用fastjson
        //1.1 把map变成json字符串
        String jsonString = JSONObject.toJSONString(stringObjectMap);
        //1.2 把json字符串转换对象
        Hospital hospital = JSONObject.parseObject(jsonString, Hospital.class);
        //2 根据医院编号查询医院信息是否已经添加
        Hospital existHospital = hospitalRepository.findByHoscode(hospital.getHoscode());
        //如果已经添加过，修改
        if(existHospital != null) {
            //设置id
            hospital.setId(existHospital.getId());
            hospital.setCreateTime(existHospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        } else {
            //如果没有添加，进行添加操作
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            //调用方法添加
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital readByHoscode(String hoscode) {
        return hospitalRepository.findByHoscode(hoscode);
    }


}
