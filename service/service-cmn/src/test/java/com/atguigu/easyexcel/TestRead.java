package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //读取路径
        String filename = "E:\\data\\dataDemo\\javademo\\yygh-download\\yygh-test.xlsx";
        //调用方法
        EasyExcel.read(filename, UserData.class, new ExcelListener()).sheet()
                .doRead();
    }
}
