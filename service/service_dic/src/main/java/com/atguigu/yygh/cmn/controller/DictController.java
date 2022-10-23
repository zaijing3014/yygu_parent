package com.atguigu.yygh.cmn.controller;


import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.attguigu.abcommon.config.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
}

