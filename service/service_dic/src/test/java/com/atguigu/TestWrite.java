package com.atguigu;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzj
 * @date 2022/10/21
 */
public class TestWrite {
    public static void main(String[] args) {
        String fileName = "D:\\0620.xlsx";
        EasyExcel.write(fileName,Stu.class).sheet("写操作").doWrite(data());
        }
//    创建数据
private static List<Stu> data(){
        List<Stu> stuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        Stu stu = new Stu();
        stu.setSno(i);
        stu.setSname("stu"+i);
        stuList.add(stu);
        }
        return stuList;
        }
        }
