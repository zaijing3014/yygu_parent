package com.atguigu;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author zzj
 * @date 2022/10/21
 */
@Data
public class Stu {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;

    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;
}