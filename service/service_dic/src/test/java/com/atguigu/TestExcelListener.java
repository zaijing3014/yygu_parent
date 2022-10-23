package com.atguigu;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zzj
 * @deprecated  AnalysisEventListener
 * @date 2022/10/21
 */
public class TestExcelListener extends AnalysisEventListener<Stu> {
    List<Stu> stuList = new ArrayList<>();
    @Override
    public void invoke(Stu stu, AnalysisContext analysisContext) {
        System.out.println("stu = " + stu);
        stuList.add(stu);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("读取表头信息"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (Stu stu : stuList) {
            System.out.println("stu = " + stu);
        }
    }
}
