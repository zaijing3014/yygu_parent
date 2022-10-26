package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.attguigu.abcommon.config.YyghException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 医院设置表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-10-17
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public String getSignKey(String hoscode) {
        HospitalSet hospitalSet = this.getByHoscode(hoscode);
        if(null == hospitalSet) {
            throw new YyghException("失败",20001);
        }
        return hospitalSet.getSignKey();
    }

    /**
     * 根据hoscode获取医院设置
     */
    private HospitalSet getByHoscode(String hoscode) {
        return baseMapper.selectOne(new QueryWrapper<HospitalSet>().eq("hoscode", hoscode));
    }
}
