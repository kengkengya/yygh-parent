package com.atguigu.yygh.user.api;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygd.common.utils.AuthContextHolder;
import com.atguigu.yygh.model.user.Patient;
import com.atguigu.yygh.user.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags="就诊人管理")
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    @Autowired
    private PatientService patientService;

    @ApiOperation("获取就诊人列表")
    @GetMapping("auth/findAll")
    public Result list(HttpServletRequest request) {
        //获取当前用户id
        Long id = AuthContextHolder.getId(request);
        System.out.println(id);
        List<Patient> list = patientService.findAllUserId(id);
        return Result.ok(list);
    }

    @ApiOperation("添加就诊人")
    @PostMapping("auth/save")
    public Result save(@RequestBody Patient patient, HttpServletRequest request) {
        Long id = AuthContextHolder.getId(request);
        patient.setId(id);
        patientService.save(patient);
        return Result.ok();
    }

    @ApiOperation("根据就诊人id查找就诊人信息")
    @GetMapping("auth/get/{id}")
    public Result getById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return Result.ok(patient);

    }

    @ApiOperation("删除就诊人")
    @DeleteMapping("auth/remove/{id}")
    public Result remove(@PathVariable Long id) {
        patientService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("更新就诊人信息")
    @PostMapping("auth/update")
    public Result update(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    @ApiOperation("根据就诊人id获取就诊人信息")
    @GetMapping("inner/get/{id}")
    public Patient getPatientOrder(@PathVariable Long id){
        Patient patient = patientService.getPatientById(id);
        return patient;
    }
}
