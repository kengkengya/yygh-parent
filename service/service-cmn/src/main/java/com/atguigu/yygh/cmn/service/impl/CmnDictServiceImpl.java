package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.CmnDictMapper;
import com.atguigu.yygh.cmn.service.CmnDictService;

import com.atguigu.yygh.model.cmn.Dict;

import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CmnDictServiceImpl extends ServiceImpl<CmnDictMapper, Dict> implements CmnDictService {

    /**
     * 查询当前id下的子数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        //向dict里面设置hasChildren的值
        for (Dict dict : dicts) {
            Long dictId = dict.getId();
            boolean b = isChildren(dictId);
            dict.setHasChildren(b);
        }
        return dicts;
    }

    /**
     * 下载功能
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载 相关内容
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = "dict";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            //查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }

            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入数据字典
     *
     * @param file
     */
    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dictCode
     * @param value
     * @return
     */
    @Override
    public String getDictName(String dictCode, String value) {

        //如果dictCode为空
        if (StringUtils.isEmpty(dictCode)) {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            if (dict != null) {
                return dict.getName();
            }
        } else {
            //根据dictcode查询dict对象，得到dict的 id值
            Dict codeDict = this.getDictByCode(dictCode);
            Long parentId = codeDict.getId();
            System.out.println(parentId);
            System.out.println(value);
            //根据parentId和value值查询name
            Dict dict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentId)
                    .eq("value", value));
            return dict.getName();

        }
        return "";
    }

    /**
     * 根据dictCode查找下级节点
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        //先根据dict_code得到自己的id
        System.out.println("findByDictCode-----"+dictCode);
        Dict dict = this.getDictByCode(dictCode);
        Long dictId = dict.getId();
        //在找到当前id下的所有子数据
        return this.findChildData(dictId);
    }

    /**
     * 根据dict_code查询Dict
     *
     * @param dictCode
     * @return
     */
    private Dict getDictByCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict;
    }

    /**
     * 查询当前id是否有子数据
     *
     * @param id
     * @return
     */
    public boolean isChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }


}
