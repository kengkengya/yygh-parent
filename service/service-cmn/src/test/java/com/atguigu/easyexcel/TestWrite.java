package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        //构建一个数据集合
        List<UserData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("张三" + i);
            list.add(data);
        }
        //设置excel文件路径和名称
        String filename = "E:\\data\\dataDemo\\javademo\\yygh-download\\yygh-test.xlsx";

        //调用方法实现写操作
        EasyExcel.write(filename, UserData.class).sheet("用户信息")
                .doWrite(list);

    }
}
