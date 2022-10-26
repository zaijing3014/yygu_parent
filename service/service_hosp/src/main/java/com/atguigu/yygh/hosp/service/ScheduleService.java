package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/26
 */
public interface ScheduleService {

    void save(Map<String, Object> stringObjectMap);

    Page<Schedule> selectPage(int current, int limit, ScheduleQueryVo scheduleQueryVo);

    void delete(String hoscode, String hosScheduleId);
}
