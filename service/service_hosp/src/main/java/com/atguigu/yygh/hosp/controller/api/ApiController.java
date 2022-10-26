package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import com.attguigu.abcommon.config.MD5;
import com.attguigu.abcommon.config.R;
import com.attguigu.abcommon.config.Result;
import com.attguigu.abcommon.config.YyghException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/25
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "上传医院")
    @PostMapping("/saveHospital")
    public R selectList(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);

//        上传到mongodb前需要签名校验
        String hoscode = (String)stringObjectMap.get("hoscode");
        if (StringUtils.isEmpty(hoscode)){
            throw new YyghException("失败",20001);
        }
//        获取signkey 是进行MD5加密的
        String sign = (String)stringObjectMap.get("sign");
        String signKeyMD5 = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if(!sign.equals(signKeyMD5)) {
            throw new YyghException("校验失败",20001);
        }
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String)stringObjectMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        stringObjectMap.put("logoData",logoData);
        hospitalService.save(stringObjectMap);
        return R.ok().code(200);
    }

//    查询接口
    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result hospital(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String)stringObjectMap.get("hoscode");
        if (StringUtils.isEmpty(hoscode)){
            throw new YyghException("失败",20001);
        }
        Hospital hospital =  hospitalService.readByHoscode(hoscode);
        return Result.ok(hospital);
    }

    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        //签名校验
        departmentService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));
        //签名校验
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.selectPage(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

//    @Autowired
//    private ScheduleService scheduleService;
//    @ApiOperation(value = "上传排班")
//    @PostMapping("saveSchedule")
//    public Result saveSchedule(HttpServletRequest request) {
//        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//        //必须参数校验 略
//        String hoscode = (String)paramMap.get("hoscode");
//        scheduleService.save(paramMap);
//        return Result.ok();
//    }
//
//    @ApiOperation(value = "获取排班分页列表")
//    @PostMapping("schedule/list")
//    public Result schedule(HttpServletRequest request) {
//        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//        //必须参数校验 略
//        String hoscode = (String)paramMap.get("hoscode");
//        //非必填
//        String depcode = (String)paramMap.get("depcode");
//        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
//        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));
//        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
//        scheduleQueryVo.setHoscode(hoscode);
//        scheduleQueryVo.setDepcode(depcode);
//        Page<Schedule> pageModel = scheduleService.selectPage(page , limit, scheduleQueryVo);
//        return Result.ok(pageModel);
//    }

//    @ApiOperation(value = "删除排班")
//    @PostMapping("schedule/remove")
//    public Result removeSchedule(HttpServletRequest request) {
//        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//        //必须参数校验 略
//        String hoscode = (String)paramMap.get("hoscode");
//        //必填
//        String hosScheduleId = (String)paramMap.get("hosScheduleId");
//        scheduleService.remove(hoscode, hosScheduleId);
//        return Result.ok();
//    }

    @ApiOperation(value = "上传排班数据")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String hoscode = (String)stringObjectMap.get("hoscode");
        if (!StringUtils.isEmpty(hoscode)){
            scheduleService.save(stringObjectMap);
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "查询排班数据")
    @PostMapping("/schedule/list")
    public Result scheduleList(HttpServletRequest request){
        //转换请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        //获取分页参数 有就用 没有就先初始化参数
        int current = StringUtils.isEmpty(stringObjectMap.get("page")) ? 1: Integer.parseInt((String)stringObjectMap.get("page")) ;
        int limit = StringUtils.isEmpty(stringObjectMap.get("limit"))? 10 : Integer.parseInt((String)stringObjectMap.get("limit")) ;
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setDepcode((String)stringObjectMap.get("depcode"));
        scheduleQueryVo.setHoscode((String)stringObjectMap.get("hoscode"));
        Page<Schedule> schedulePage = scheduleService.selectPage(current, limit, scheduleQueryVo);
        return Result.ok(schedulePage);
    }

    @ApiOperation(value = "删除排班数据")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        //转换请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String hoscode = (String)stringObjectMap.get("hoscode");
        String hosScheduleId = (String)stringObjectMap.get("hosScheduleId");
        scheduleService.delete(hoscode,hosScheduleId);
        return Result.ok();
    }
}
