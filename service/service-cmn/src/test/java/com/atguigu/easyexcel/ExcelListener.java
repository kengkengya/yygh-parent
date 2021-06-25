package com.atguigu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;


public class ExcelListener extends AnalysisEventListener<UserData> {

    //一行一行开始读取excel内容
    @Override
    public void invoke(UserData data, AnalysisContext context) {
        System.out.println(data);
    }

    //读取表头信息
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);
        System.out.println("表头信息："+headMap);
    }

    //读取之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
