package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zzj
 * @date 2022/10/23
 */

public class ReadListenerAll extends AnalysisEventListener<DictEeVo> {

    private DictService dictService;

    public ReadListenerAll(DictService dictService) {
        this.dictService = dictService;
    }
    private static final int BATCH_COUNT = 2;
    private List<Dict> dictList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictList.add(dict);
        if (dictList.size() >= BATCH_COUNT){
            saveData();
            dictList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        dictService.saveBatch(dictList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
