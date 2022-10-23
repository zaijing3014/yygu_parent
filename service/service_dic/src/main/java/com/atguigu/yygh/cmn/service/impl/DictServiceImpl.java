package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-10-21
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> getListById(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        dictList.forEach(dict->
        {
            Long dictId = dict.getId();
            boolean childStatus = this.isChildren(dictId);
            dict.setHasChildren(childStatus);
        });
        return dictList;
    }

    @Override
    public void exportData(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
//        指定encoding 防止乱码
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
        response.addHeader("Access-Control-Allow-Origin","*");
        List<Dict> dicList = baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList = new ArrayList<>();
        for (Dict dict : dicList) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet().doWrite(dictEeVoList);
    }

    //    判断是否有子集
    public boolean isChildren(Long dictId){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",dictId);
        Integer selectCount = baseMapper.selectCount(wrapper);
        return selectCount>0;
    }
}
