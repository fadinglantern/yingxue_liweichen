package com.weichen.controller;

import com.weichen.entity.YxCategory;
import com.weichen.entity.YxUser;
import com.weichen.entity.YxVideo;
import com.weichen.service.YxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-23
 * @changeTime 22:23
 */
@Controller
@RequestMapping("/YxVideo")
public class YxVideoController {
    @Resource
    private YxVideoService service;

    @ResponseBody
    @RequestMapping("/findVideoList")
    public HashMap<String,Object> findVideoList(Integer page, Integer rows,String cateId){
        return service.getVideoPageList(page, rows);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public String edit(String oper, YxVideo video,String id){
        String result =null;
        if ("add".equals(oper)){
            result= service.addVideo(video);
        }
        if ("alter".equals(oper)){
            service.modifyState(video);
        }
        if ("del".equals(oper)){
            service.deleteAliyunVideo(id);
        }
        if ("edit".equals(oper)){
            result= service.modifyOneVideo(video);
        }
        return result;
    }

    @RequestMapping("videoAndFirstFrameUpload")
    public void videoAndFirstFrameUpload(MultipartFile videoPath, String id) throws IOException {
//        service.videoUpload(videoPath, id);
        service.videoAndFirstFrameUploadAliyun(videoPath, id);
    }


}
