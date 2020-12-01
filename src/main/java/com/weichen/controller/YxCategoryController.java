package com.weichen.controller;

import com.alibaba.druid.util.StringUtils;
import com.aliyuncs.exceptions.ClientException;
import com.weichen.entity.YxCategory;
import com.weichen.service.YxCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-20
 * @changeTime 17:47
 */
@Controller
@RequestMapping("/YxCategory")
public class YxCategoryController {
    private static final Logger log = LoggerFactory.getLogger(YxUserController.class);
    private HashMap<String,Object> map;
    @Autowired
    private YxCategoryService service;

    @ResponseBody
    @RequestMapping("/findCategoryList")
    public HashMap<String,Object> findCategoryFList(Integer page, Integer rows,String parentId){
        map= new HashMap<>();
        YxCategory category=new YxCategory();
        if (parentId!=null){
            map = service.getSonPageListByFId (category,parentId,page, rows);//总条数
        }else {
            map = service.getFatherPageList (page, rows);//总条数
        }
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public HashMap<String,Object> edit(YxCategory category, String oper){
        HashMap<String,Object> map=new HashMap<>();
        //判断是什么操作
        if(StringUtils.equals("add",oper)){
            service.saveOneData(category);
        }
        if(StringUtils.equals("edit",oper)){
            service.modifyOneData(category);
        }
        if(StringUtils.equals("del",oper)){
            map= service.deleteOneData(category);
        }
        return map;
    }
    @RequestMapping("/putPhoneCode")
    @ResponseBody
    public HashMap<String,Object> putPhoneCode(String phoneNum) throws ClientException {
        HashMap<String,Object> map=new HashMap<>();
        String message=service.putPhoneCode(phoneNum);
        if ("发送成功".equals(message)){
            map.put("message",message);
            map.put("status","200");
        }else {
            map.put("message",message);
            map.put("status","201");
        }
        return map;
    }
}
