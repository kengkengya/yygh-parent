package com.atguigu.yygh.oss.controller;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@Api("文件上传")
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    //上传文件到阿里云oss
    @Autowired
    private FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file){

        String url = fileService.upload(file);

        return Result.ok(url);

    }
}
