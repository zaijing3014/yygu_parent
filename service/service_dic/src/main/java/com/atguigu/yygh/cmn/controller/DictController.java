package com.atguigu.yygh.cmn.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.ReadListenerAll;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.attguigu.abcommon.config.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-10-21
 */

@Api(tags = "字典接口")
@RestController
@CrossOrigin
@RequestMapping("admin/cmn/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @ApiOperation(value = "通过id查询字典")
    @GetMapping("/findChildData/{id}")
    public R findChildData(@PathVariable Long id){
        List<Dict> dicList = dictService.getListById(id);
        return R.ok().data(dicList);
    }

    @GetMapping(value = "/exportData")
    @ApiOperation(value="导出")
    public void exportData(HttpServletResponse response) throws IOException {
        dictService.exportData(response);
    }

    @PostMapping("/importData")
    @ApiOperation(value = "导入")
    //注意导入的接受参数固定是 MultipartFile
    public R importData(MultipartFile file){
        dictService.importData(file);
        return R.ok();
    }

    @PostMapping("/importDataAll")
    @ApiOperation(value = "导入")
    public R importDataAll(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new ReadListenerAll(dictService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
//    @PostMapping("/importDataAll")
//    @ApiOperation(value = "导入")
//    //注意导入的接受参数固定是 MultipartFile  高并发情况下的导入
//    public R importDataAll(MultipartFile file){
//        try {
//            EasyExcel.read(file.getInputStream(), DictEeVo.class,new ReadListenerAll(dictService)).sheet().doRead();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return R.ok();
//    }
}

