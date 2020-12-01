package com.weichen.controller;

import com.weichen.entity.SexCity;
import com.weichen.entity.YxUser;
import com.weichen.service.YxUserService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author weichenLi
 * @create 2020-11-18
 * @changeTime 22:05
 */
@Controller
@RequestMapping("/yxUser")
public class YxUserController {
    @Resource
    private YxUserService service;
    private static final Logger log = LoggerFactory.getLogger(YxUserController.class);
    private HashMap<String,Object> map;
    @ResponseBody
    @RequestMapping("/showAllUser")
    public HashMap<String,Object> showAllUser(Integer page, Integer rows){
        /*log.info("当前页: {},每页显示记录数: {}",page,rows);
        map= new HashMap<>();
        List<YxUser> userList = service.findByPage(page, rows);
        //总条数
        Long totalCounts = service.findTotalCounts();
        //总页数
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",userList);
        session.setAttribute("InfoUserMap",map);*/
        return service.findByPage(page,rows);
    }
    @ResponseBody
    @RequestMapping("/edit")
    public String edit(String oper,YxUser user){
        String result =null;
        if ("alter".equals(oper)){
            service.modifyState(user);
        }
        if ("add".equals(oper)){
            result= service.addUser(user);
        }
        if ("edit".equals(oper)){
            result=service.modifyOneUser(user);
        }
        if ("del".equals(oper)){
            service.deleteOneUser(user);
        }
        return result;
    }

    @RequestMapping("photoUpload")
    public void photoUpload(MultipartFile picImg, String id) {
//        service.photoUpload(picImg, id);
        service.photoUploadAliyun(picImg, id);
    }
    @RequestMapping("/expdpUser")
    @ResponseBody
    public HashMap<String,Object> expdpUser() {
        HashMap<String,Object> map=new HashMap<>();
//        String message=service.pOIExpdpUser();
        String message=service.ePOIExpdpUser();
        map.put("message",message);
        return map;

    }
    @RequestMapping("/sexCount")
    @ResponseBody
    public HashMap<String,Object> sexCount() {

        return service.getSexCounts();

    }

    @RequestMapping("/sexCityCount")
    @ResponseBody
    public List<SexCity> sexCityCount() {

        return service.getSexCityCounts();

    }

}
