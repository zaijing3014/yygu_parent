package com.atguigu;

import com.alibaba.excel.EasyExcel;

/**
 * @author zzj
 * @date 2022/10/21
 */
public class TestRead {
    public static void main(String[] args) {
        EasyExcel.read("D:\\0620.xlsx",Stu.class,new TestExcelListener()).sheet(0).doRead();
    }
}
