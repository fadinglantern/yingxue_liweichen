package com.weichen;

import com.alibaba.fastjson.JSON;
import com.weichen.dao.YxUserDao;
import com.weichen.entity.YxUser;
import com.weichen.log.annotation.YingxLog;
import com.weichen.service.YxUserService;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-29
 * @changeTime 22:00
 */
@SpringBootTest
public class test01 {
    @Resource
    private YxUserService service;
    @Resource
    private YxUserDao yxUserDao;
    @Test
    public void test01(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io",
                "");
        HashMap<String,Object> map=service.getSexCounts();
        List<Integer> b= (List<Integer>) map.get("boys");
        b.set(2,11);
        b.set(3,6);
        map.put("boys",b);
        String j= JSON.toJSONString(map);
        goEasy.publish("yingxChannel", j);
    }
    @Test
    public void test02() throws Exception {
        service.addUser(new YxUser());

    }
    @Test
    public void test03()  {
        YxUser user=yxUserDao.selectOneData("");
        int index=user.getPicImg().lastIndexOf("/");
        String picName=user.getPicImg().substring(index+1);
        int index2=picName.indexOf("-");
        String name=picName.substring(index2+1);
        System.out.println(picName);
        System.out.println(name);

    }
    @Test
    public void test04()  {
       System.out.println("hello world");
    }
}
