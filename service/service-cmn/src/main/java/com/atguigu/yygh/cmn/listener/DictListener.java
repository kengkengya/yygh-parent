package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.CmnDictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {
    private CmnDictMapper dictMapper;

    public DictListener(CmnDictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //一行一行的读取
    @Override
    public void invoke(DictEeVo data, AnalysisContext context) {
        //调用方法
        Dict dict = new Dict();
        BeanUtils.copyProperties(data,dict);
        dictMapper.insert(dict);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
