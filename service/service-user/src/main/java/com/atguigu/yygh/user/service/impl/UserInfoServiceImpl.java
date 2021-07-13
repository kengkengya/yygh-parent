package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygd.common.exception.YyghException;
import com.atguigu.yygd.common.helper.JwtHelper;
import com.atguigu.yygd.common.result.ResultCodeEnum;

import com.atguigu.yygh.enums.AuthStatusEnum;
import com.atguigu.yygh.model.user.Patient;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.model.user.UserLoginRecord;
import com.atguigu.yygh.user.mapper.UserInfoMapper;
import com.atguigu.yygh.user.service.PatientService;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.LoginVo;
import com.atguigu.yygh.vo.user.UserAuthVo;
import com.atguigu.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService  {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private PatientService patientService;

    /**
     * 实现手机号登录
     * @param loginVo
     * @return
     */
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {

        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(code)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        //TODO 校验校验验证码
        String mobleCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(mobleCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }
        //绑定手机号码
        UserInfo userInfo = null;
        if(!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.getByOpenid(loginVo.getOpenid());
            if(null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
        }
        if(null == userInfo) {
            //手机号已被使用
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            //获取会员
            userInfo = baseMapper.selectOne(queryWrapper);
            if(null == userInfo) {
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }
        }


        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //TODO 记录登录
//        UserLoginRecord userLoginRecord = new UserLoginRecord();
//        userLoginRecord.setUserId(userInfo.getId());
//        userLoginRecord.setIp(loginVo.getIp());
//        userLoginRecordMapper.insert(userLoginRecord);


        //返回页面显示名称
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("name", name);
        map.put("token", token);
        return map;

    }

    /**
     * 根据openid判断数据库中是否存在此用户
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo getByOpenid(String openid) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 用户授权认证
     * @param id
     * @param userAuthVo
     */
    @Override
    public void userAuth(Long id, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = baseMapper.selectById(id);
        //设置认证信息
        userInfo.setName(userInfo.getName());
        System.out.println(userInfo.getName());
        //其他认证信息
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //信息更新
        baseMapper.updateById(userInfo);
    }

    //用户列表（条件查询带分页）
    @Override
    public IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo) {
        //UserInfoQueryVo获取条件值
        String name = userInfoQueryVo.getKeyword(); //用户名称
        Integer status = userInfoQueryVo.getStatus();//用户状态
        Integer authStatus = userInfoQueryVo.getAuthStatus(); //认证状态
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin(); //开始时间
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd(); //结束时间
        //对条件值进行非空判断
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(authStatus)) {
            wrapper.eq("auth_status",authStatus);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper的方法
        IPage<UserInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        //编号变成对应值封装
        pages.getRecords().forEach(this::packageUserInfo);
        return pages;
    }


    //用户锁定状态
    @Override
    public void lock(Long userId, Integer status) {
        if (status == 0||status==1) {
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setStatus(status);
            baseMapper.updateById(userInfo);
        }
    }

    //用户详情
    @Override
    public Map<String, Object> show(Long userId) {
        Map<String,Object> map = new HashMap<>();

        UserInfo userInfo = this.packageUserInfo(baseMapper.selectById(userId));
        map.put("userInfo",userInfo);
        List<Patient> patientList = patientService.findAllUserId(userId);
        map.put("patientList",patientList);
        return map;
    }
    //认证审批
    @Override
    public void approval(Long userId, Integer authStatus) {
        //2==通过，-1==不通过
        if(authStatus ==2 || authStatus ==-1){
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setAuthStatus(authStatus);
            baseMapper.updateById(userInfo);
        }
    }

    //封装其他参数
    private UserInfo packageUserInfo(UserInfo item) {
        //处理认证状态编码
        item.getParam().put("authStatusString",AuthStatusEnum.getStatusNameByStatus(item.getAuthStatus()));
        //处理用户状态
        String status = item.getStatus() ==0?"锁定":"正常";
        item.getParam().put("status",status);
        return item;
    }

}
