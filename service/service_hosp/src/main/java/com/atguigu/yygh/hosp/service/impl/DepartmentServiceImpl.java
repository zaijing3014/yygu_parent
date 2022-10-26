package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.attguigu.abcommon.config.YyghException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/10/25
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    //科室添加接口
    @Override
    public void save(Map<String, Object> paramMap) {
        // paramMap变成对象
        String jsonString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);
        if(department == null) {
            throw new YyghException("数据错误",20001);
        }
        //查询科室是否存在  医院编号 + 科室编号
        Department existsDepartment = departmentRepository.
                findByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());
        //判断
        if(existsDepartment != null) { //修改
            department.setId(existsDepartment.getId());
            department.setCreateTime(existsDepartment.getCreateTime());
            department.setUpdateTime(new Date());
            departmentRepository.save(department);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page-1, limit, sort);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        //创建实例
        Example<Department> example = Example.of(department, matcher);
        Page<Department> pages = departmentRepository.findAll(pageable);
        return pages;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(null != department) {
            departmentRepository.deleteById(department.getId());
        }
    }
}