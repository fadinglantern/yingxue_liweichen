package com.weichen.service;

import com.weichen.entity.YxCategory;
import com.weichen.entity.YxUser;
import com.weichen.entity.YxVideo;
import com.weichen.po.CateVideoPO;
import com.weichen.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-23
 * @changeTime 22:20
 */
public interface YxVideoService {
    //分页查询
    HashMap<String,Object> getVideoPageList(Integer page, Integer rows);
    //获取全部行数
    Long findTotalCounts();
    //更改state 字段
    void modifyState(YxVideo video);
    //数据库添加 video
    String addVideo(YxVideo video);
    //本地添加 视频 封面; 数据库添加本地路径
    void videoUpload(MultipartFile videoPath, String id);
    //云端添加 视频 封面; 数据库添加云端 视频封面网络连接
    void videoAndFirstFrameUploadAliyun(MultipartFile videoObject, String id) throws IOException;
    //云端删除
    void deleteAliyunVideo(String id);
    //修改数据库
    String modifyOneVideo(YxVideo video);

    //对调接口
    List<VideoPO> queryByReleaseTime();
    List<VideoPO> queryCateVideoList(String cateId);
    List<VideoPO> queryByLikeVideoName(String content);
}
