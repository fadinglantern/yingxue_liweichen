package com.weichen.controller;

import com.weichen.entity.YxCategory;
import com.weichen.log.service.YxLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 23:39
 */
@Controller
@RequestMapping("/YxLog")
public class YxLogController {
    @Resource
    private YxLogService service;
    @ResponseBody
    @RequestMapping("/findLogList")
    public HashMap<String,Object> findCategoryFList(Integer page, Integer rows, String parentId){
        HashMap<String,Object> map= new HashMap<>();
        map = service.seleteByPage(page, rows);//总条数
        return map;
    }
}
