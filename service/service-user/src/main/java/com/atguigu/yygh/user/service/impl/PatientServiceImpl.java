package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.enums.DictEnum;
import com.atguigu.yygh.model.user.Patient;
import com.atguigu.yygh.user.mapper.PatientMapper;
import com.atguigu.yygh.user.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Autowired
    private DictFeignClient dictFeignClient;
    //获取就诊人列表
    @Override
    public List<Patient> findAllUserId(Long id) {
        //根据userid查询所有就诊人列表

        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<Patient> patientList = baseMapper.selectList(queryWrapper);
        //通过远程调用数据字典，转换成正确的内容
        //其他参数封装
        System.out.println(patientList);
        patientList.forEach(this::packPatient);

        return patientList;
    }

    //根据就诊人id查询
    @Override
    public Patient getPatientById(Long id) {
        Patient patient = baseMapper.selectById(id);
        this.packPatient(patient);
        return patient;
    }

    //其他参数封装
    private void packPatient(Patient patient) {
        //根据dictcode和value进行查询对应值
        //联系人证件
        String certificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());

        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(),patient.getContactsCertificatesType());
        //省
        String provinceString = dictFeignClient.getName(null,patient.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(null,patient.getCityCode());
        //区
        String districtString = dictFeignClient.getName(null,patient.getDistrictCode());
        //将其封装到其他参数中
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());
    }
}
