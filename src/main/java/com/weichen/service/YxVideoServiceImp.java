package com.weichen.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.weichen.controller.YxUserController;
import com.weichen.dao.YxCategoryDao;
import com.weichen.dao.YxVideoDao;
import com.weichen.entity.YxCategory;
import com.weichen.entity.YxUser;
import com.weichen.entity.YxVideo;
import com.weichen.po.CateVideoPO;
import com.weichen.po.VideoPO;
import com.weichen.util.AliyunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author weichenLi
 * @create 2020-11-23
 * @changeTime 22:21
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class YxVideoServiceImp implements YxVideoService {
    private static final Logger log = LoggerFactory.getLogger(YxUserController.class);
    private String bucketName="yxmyvideo";
    private String dirVideoFile="uploadFiles/video/";
    private String dirCoverFile="uploadFiles/cover/";
    @Resource
    private YxVideoDao dao;
    @Resource
    private HttpServletRequest request;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Long findTotalCounts() {
        return dao.findTotalCounts();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String,Object> getVideoPageList(Integer page, Integer rows) {
        HashMap<String,Object> map= new HashMap<>();
        List<YxVideo> list= dao.selectVideoPageList((page-1)*rows, rows);//总条数
        Long totalCounts = dao.findTotalCounts();
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",list);
        return map;
    }

    @Override
    public void modifyState(YxVideo video) {
        dao.updateVideoState(video);
    }


    @Override
    public String addVideo(YxVideo video) {
        String id= UUID.randomUUID().toString().replace("-", "");
        video.setId(id);
        video.setUploadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        video.setState(1);
        dao.insertOneVideo(video);
        return id;
    }

    /**文件上传本地的方法
     *
    * */
    @Override
    public void videoUpload(MultipartFile videoPath, String id) {
        //文件上传
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFiles/video");
        String dateDir = new SimpleDateFormat("yyyy-MM").format(new Date()); //创建月份目录
        //2.判断上传文件夹是否存在
        File file = new File(realPath,dateDir);
        if (!file.exists()) {
            file.mkdirs();//创建文件夹
        }
        //3.获取文件名
        String filename = videoPath.getOriginalFilename();
        //给文件拼接时间戳
        String newName = (new Date().getTime()) + "-" + filename;
        //4.文件上传
        try {
            videoPath.transferTo(new File(realPath+"\\"+dateDir, newName));//到路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.数据修改
        YxVideo video=new YxVideo();
        video.setId(id);
        video.setVideoPath(newName);
        dao.updateVideoPath(video);
    }

    @Override
    public void videoAndFirstFrameUploadAliyun(MultipartFile videoObject, String id) throws IOException {
        System.out.println(id);
        if (Objects.requireNonNull(videoObject.getOriginalFilename()).length()!=0){//是否上传了 视频
            YxVideo video=dao.selectOneVideo(id);
            if (video.getVideoPath()==null){//添加
                System.out.println("进入 添加视频");
                //上传视频 并获得处理好的 视频名
                String newVideoName = AliyunUtil.uploadVideoAliyun(videoObject,bucketName,dirVideoFile);
                //截取视频封面 返回封面网络路径
                String url=AliyunUtil.cutVideoFirstFrame(bucketName,newVideoName,dirVideoFile).toString();
                //上传封面 返回处理好的 封面名
                String cutPhoto= AliyunUtil.uploadVideoFirstFrame(bucketName,videoObject.getOriginalFilename(),url,dirCoverFile);

                video.setId(id);
                video.setVideoPath("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/"+dirVideoFile+newVideoName);
                video.setCoverPath("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/"+dirCoverFile+cutPhoto);
                //存入数据库
                dao.updateVideoPath(video);
            } else {//修改
                //获取文件名
                String videoName=video.getVideoPath().substring(video.getVideoPath().lastIndexOf("/")+1);
                String firstFrameName=video.getCoverPath().substring(video.getCoverPath().lastIndexOf("/")+1);
                String originalVideoName=videoName.substring(videoName.indexOf("-")+1);
//                String originalCoverName=firstFrameName.substring(firstFrameName.indexOf("-")+1);
                if (!(originalVideoName.equals(videoObject.getOriginalFilename()))){
                    System.out.println("进入 修改视频");
                    //删除阿里云内数据
                    AliyunUtil.deleteFile(bucketName, videoName,dirVideoFile);
                    AliyunUtil.deleteFile(bucketName, firstFrameName,dirCoverFile);

                    //上传视频 并获得处理好的 视频名
                    String newVideoName = AliyunUtil.uploadVideoAliyun(videoObject,bucketName,dirVideoFile);
                    //截取视频封面 返回封面网络路径
                    String url=AliyunUtil.cutVideoFirstFrame(bucketName,newVideoName,dirVideoFile).toString();
                    //上传封面 返回处理好的 封面名
                    String cutPhoto= AliyunUtil.uploadVideoFirstFrame(bucketName,videoObject.getOriginalFilename(),url,dirCoverFile);

                    video.setId(id);
                    video.setVideoPath("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/"+dirVideoFile+newVideoName);
                    video.setCoverPath("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/"+dirCoverFile+cutPhoto);
                    //存入数据库
                    dao.updateVideoPath(video);
                }
                System.out.println("进入 修改视频 但视频相同");
            }
        }
    }

    @Override
    public void deleteAliyunVideo(String id) {

        YxVideo video=dao.selectOneVideo(id);//全部属性
        int index1 =video.getVideoPath().lastIndexOf("/");
        int index2 =video.getCoverPath().lastIndexOf("/");
        String videoName=video.getVideoPath().substring(index1+1);
        String firstFrameName=video.getCoverPath().substring(index2+1);
        System.out.println(videoName);
        System.out.println(firstFrameName);
        //删除阿里云内数据
        AliyunUtil.deleteFile(bucketName, videoName,dirVideoFile);
        AliyunUtil.deleteFile(bucketName, firstFrameName,dirCoverFile);
        //删除数据库内数据
        dao.deleteOneVideo(id);
    }

    @Override
    public String modifyOneVideo(YxVideo video) {
        YxVideo all= dao.selectOneVideo(video.getId());
        all.setTitle(video.getTitle());
        all.setBrief(video.getBrief());
        all.setCategoryId(video.getCategoryId());
        all.setUserId(video.getUserId());
        dao.updateVideoData(all);
        return all.getId();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> list=dao.queryByReleaseTime();
        for (int i = 0; i < list.size(); i++) {
             String id=list.get(i).getId();
             //查询redis 点赞数
            list.get(i).setLikeCount(3);

        }
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoPO> queryCateVideoList(String cateId) {
        List<VideoPO> list=dao.queryCateVideoList(cateId);
        for (int i = 0; i <list.size(); i++) {
            VideoPO videoPO=list.get(i);
            //查询redis 点赞数
             videoPO.setLikeCount(3);
        }
        return list;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoPO> queryByLikeVideoName(String content) {
        List<VideoPO> list=dao.queryByLikeVideoName("%"+content+"%");
        for (int i = 0; i < list.size(); i++) {
            VideoPO po=list.get(i);
            //查询redis 点赞数
             po.setLikeCount(3);
        }
        return list;
    }
}
