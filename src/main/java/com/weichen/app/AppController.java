package com.weichen.app;

import com.weichen.common.CommonResult;
import com.weichen.po.CateVideoPO;
import com.weichen.po.CategoryPO;
import com.weichen.po.VideoPO;
import com.weichen.service.YxCategoryService;
import com.weichen.service.YxVideoService;
import com.weichen.util.AliyunUtil;
import com.weichen.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-25
 * @changeTime 17:26
 */
@RestController
@RequestMapping("/app")
public class AppController {
    @Resource
    private YxVideoService videoService;
    @Resource
    private YxCategoryService categoryService;

    @RequestMapping("/getPhoneCode")
    public Object getPhoneCode(String phone){
        String code= ImageCodeUtil.getSecurityCode(6,
                ImageCodeUtil.SecurityCodeLevel.Simple,false);
        String signName="AGS课堂";
        String templateCode="SMS_205621621";
        String message= null;
        try {
            message=AliyunUtil.sendPhoneCode(phone,signName,templateCode,code);
            return new CommonResult().success(message,phone);
        }catch (Exception e){
            return new CommonResult().failed(message);
        }
    }
    @RequestMapping("/queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        try {
            List<VideoPO> videoPOS = videoService.queryByReleaseTime();
            return new CommonResult().success(videoPOS);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }
    @RequestMapping("/queryAllCategory")
    public CommonResult queryAllCategory(){
        try{
            List<CategoryPO> list=categoryService.queryAllCategory();
            return new CommonResult().success(list);
        }catch (Exception e){
            return new CommonResult().failed();
        }
    }
    @RequestMapping("/queryCateVideoList")//
    public CommonResult queryCateVideoList(String cateId){
        try{
            List<VideoPO> list=videoService.queryCateVideoList(cateId);
            return new CommonResult().success(list);
        }catch (Exception e){
            return new CommonResult().failed();
        }
    }
    @RequestMapping("/queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        try{
            List<VideoPO> list=videoService.queryByLikeVideoName(content);
            return new CommonResult().success(list);
        }catch (Exception e){
            return new CommonResult().failed();
        }
    }
}
