package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.ScheduleRepository;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/26
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public void save(Map<String, Object> stringObjectMap) {
        //将 stringObjectMap map集合转换成 Schedule 对象
        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Schedule scheduleObject = JSONObject.parseObject(jsonString,Schedule.class);
        //根据 医院编号 和 排班编号 查询是否存在 有增修改 无则添加
        String hosCode = (String)stringObjectMap.get("hoscode");
        String hosScheduleId = (String)stringObjectMap.get("hosScheduleId");
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hosCode,hosScheduleId);
        if (schedule != null){
            scheduleObject.setCreateTime(new Date());
            scheduleObject.setUpdateTime(new Date());
            scheduleObject.setIsDeleted(0);
            scheduleObject.setStatus(1);
            scheduleRepository.save(scheduleObject);
        } else {
            scheduleObject.setUpdateTime(new Date());
            scheduleObject.setIsDeleted(0);
            scheduleObject.setStatus(1);
            scheduleRepository.save(scheduleObject);
        }
    }

    @Override
    public Page<Schedule> selectPage(int current, int limit, ScheduleQueryVo scheduleQueryVo) {
        //设置排序方式
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //用PageRequest封装 分页排序参数
        PageRequest pageRequest = PageRequest.of(current-1, limit, sort);
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageRequest);
        return schedulePage;
    }

    @Override
    public void delete(String hoscode, String hosScheduleId) {
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule != null){
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}
