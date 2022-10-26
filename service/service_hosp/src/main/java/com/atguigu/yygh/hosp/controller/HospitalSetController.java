package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.attguigu.abcommon.config.HttpRequestHelper;
import com.attguigu.abcommon.config.R;
import com.attguigu.abcommon.config.YyghException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-10-17
 */
@RestController
@CrossOrigin // 跨域
@RequestMapping("/admin/hosp/hospitalSet")
@Api(tags = "尚医通_医院信息接口")

public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "查询所有")
    @GetMapping("/selectAll")
    public R selectList(){
        List<HospitalSet> list = hospitalSetService.list(null);
        int size = list.size();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",size);
        map.put("data",list);
        return R.ok().data(map) ;
    }

    @ApiOperation(value = "通过id查询")
    @GetMapping("/selectById/{id}")
    public R selectList(@PathVariable Long id){
        HospitalSet hosp = hospitalSetService.getById(id);
        return R.ok().data(hosp);
    }

    @ApiOperation(value = "添加信息")
    @PostMapping("/saveHospSet")
    public R save(
            @ApiParam(required = true,value = "医院设置对象",name = "hospitalSet")
            @RequestBody HospitalSet hospitalSet){
        if (StringUtils.isEmpty(hospitalSet)){
            throw new YyghException("添加时异常",88);
        }
        String sign = System.currentTimeMillis()+""+new Random().nextInt(1000);
        hospitalSet.setSignKey(sign);
        boolean isSave = hospitalSetService.save(hospitalSet);
        if (isSave){
            HashMap<String, Object> map = new HashMap<>();
            map.put("sign",sign);
            map.put("hashcode",hospitalSet.getHoscode());
            HttpRequestHelper.sendRequest(map,"http://localhost:9998/hospSet/updateSignKey");
            return R.ok();
        } else {
            return R.error();
        }

    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public R deleteById(@PathVariable Long id){
        hospitalSetService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "带查询条件的分页")
    @PostMapping("findPageQueryHospSet/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) HospitalSetQueryVo hospitalQueryVo
            ){
        Page<HospitalSet> pageList = new Page<>(page,limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        if (hospitalQueryVo == null){
            hospitalSetService.page(pageList);
        } else {
            if (!StringUtils.isEmpty(hospitalQueryVo.getHosname())){
                queryWrapper.like("hosname",hospitalQueryVo.getHosname());
            }
            if (!StringUtils.isEmpty(hospitalQueryVo.getHoscode())){
                queryWrapper.like("hoscode",hospitalQueryVo.getHoscode());
            }
            hospitalSetService.page(pageList,queryWrapper);
        }
        long total = pageList.getTotal();
        List<HospitalSet> records = pageList.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("data",records);
        return R.ok().data(map);
    }


    @ApiOperation("修改")
    @PutMapping("/update")
    public R updateById(@RequestBody HospitalSet hospitalSet){
        boolean status = hospitalSetService.updateById(hospitalSet);
        if (status){
            return R.ok();
        } else {
            return R.error().message("更新失败");
        }
    }


    @ApiOperation("批量删除")
    @DeleteMapping("/deleteByIds")
    public R deleteByIds(
            @RequestBody List<Long> ids
    ){
        boolean status = hospitalSetService.removeByIds(ids);
        if (status){
            return R.ok().message("批量删除成功！！");
        } else {
            return R.error().message("批量删除失败！！");
        }
    }

    //医院设置锁定和解锁
    @ApiOperation("设置医院状态")
    @PutMapping("/changHosStatus/{id}/{status}")
    public R changHosStatus(@PathVariable Long id,@PathVariable Integer status ){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        boolean bool = hospitalSetService.updateById(hospitalSet);
        if (bool){
            return R.ok().message("更新状态成功！");
        } else {
            return R.error().message("更新状态失败！");
        }

    }















}

