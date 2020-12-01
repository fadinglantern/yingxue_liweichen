package com.weichen.controller;

import com.weichen.entity.YxAdmin;
import com.weichen.entity.YxUser;
import com.weichen.service.YxAdminService;
import com.weichen.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author weichenLi
 * @create 2020-11-19
 * @changeTime 17:35
 */
@Controller
@RequestMapping("/yxAdmin")
public class YxAdminController {
    @Autowired
    private YxAdminService service;
    @ResponseBody
    @RequestMapping("/adminLogin")
    public HashMap<String,Object> yxadminLogin(YxAdmin admin, String enCode, HttpSession session){
        String getCode= (String)session.getAttribute("securityCode");
        HashMap<String,Object> map=new HashMap<>();
//        System.out.println(getCode);
        if (getCode.equals(enCode)){
            YxAdmin getAdmin =service.findAdmin(admin);
            if (getAdmin==null){
                map.put("message","用户不存在");
                map.put("status","201");
            }else{
                if (admin.getPassword().equals(getAdmin.getPassword())){
                    map.put("message","登录成功");
                    map.put("status","200");
                    map.put("thisAdmin",getAdmin);
                    session.setAttribute("InfoMap",map);
                }else{
                    map.put("message","密码错误");
                    map.put("status","201");
                }
            }
        }else {
            map.put("message","验证码不正确");
            map.put("status","201");
        }
        return map;
    }
    @RequestMapping("/checkCode")
    public void createImage(HttpServletResponse response, HttpServletRequest request) {
        //获得随机字符
        String securityCode = ImageCodeUtil.getSecurityCode();
        //打印随机字符
        System.out.println("===="+securityCode);
//        map.put("securityCode",securityCode);
        request.getSession().setAttribute("securityCode",securityCode);
        //生成图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/png");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            ImageIO.write(image, "png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
