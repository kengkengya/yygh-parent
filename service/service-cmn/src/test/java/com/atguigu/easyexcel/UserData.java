package com.atguigu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData {

    //在属性上增加注解，设置表头
    @ExcelProperty(value = "用户编号", index = 0)
    private int uid;
    @ExcelProperty(value = "用户属性", index = 1)
    private String username;

}
