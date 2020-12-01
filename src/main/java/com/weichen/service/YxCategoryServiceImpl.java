package com.weichen.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sun.scenario.effect.impl.ImagePool;
import com.weichen.controller.YxUserController;
import com.weichen.dao.YxCategoryDao;
import com.weichen.dao.YxVideoDao;
import com.weichen.entity.YxCategory;
import com.weichen.entity.YxVideo;
import com.weichen.po.CategoryPO;
import com.weichen.po.VideoPO;
import com.weichen.util.AliyunUtil;
import com.weichen.util.ImageCodeUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author weichenLi
 * @create 2020-11-20
 * @changeTime 17:45
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class YxCategoryServiceImpl implements YxCategoryService{
    private static final Logger log = LoggerFactory.getLogger(YxUserController.class);
    @Resource
    private YxCategoryDao dao;
    @Resource
    private YxVideoDao videodao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String,Object> getFatherPageList(Integer page, Integer rows) {
        HashMap<String,Object> map= new HashMap<>();
        YxCategory category=new YxCategory();
        category.setParentId(null);
        category.setLevels(1);
        List<YxCategory> categories=dao.selectFatherPageList((page-1)*rows, rows);
        Long totalCounts = dao.findTotalCounts(category);
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",categories);
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String,Object> getSonPageListByFId(YxCategory category,String parentId,Integer page, Integer rows) {
        HashMap<String,Object> map= new HashMap<>();
        category.setParentId(parentId);
        category.setLevels(2);
        List<YxCategory> categories=dao.selectSonPageListByFId(category.getLevels(),category.getParentId(),(page-1)*rows, rows);
        Long totalCounts = dao.findTotalCounts(category);
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",categories);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Long findTotalCounts(YxCategory category) {
        return dao.findTotalCounts(category);
    }

    /*@Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public YxCategory getOneData(String id) {
        return dao.selectOneData(id);
    }*/

    @Override
    public void saveOneData(YxCategory category) {
        category.setId(UUID.randomUUID().toString().replace("-", ""));
        if (category.getParentId()!=null){//子类
            category.setLevels(2);
        }else{
            category.setLevels(1);
        }
        System.out.println(category);
        dao.insertOneData(category);
    }

    @Override
    public void modifyOneData(YxCategory category) {
        dao.updateOneData(category);
    }

    @Override
    public HashMap<String,Object> deleteOneData(YxCategory category) {
        HashMap<String,Object> map=new HashMap<>();
        YxCategory category1 = dao.selectOneData(category.getId());//替代
        if (category1.getLevels()==1){//父类别
            //判断类别下是否有子类别
            category1.setLevels(2);
            category1.setParentId(category1.getId());
            Long lenth= dao.findTotalCounts(category1);
            if(lenth!=0){//子类别条数为0 不可删除
                map.put("message","此类别下存在二级类别,不可删除");
            }else {
                dao.deleteOneData(category);
                map.put("message","删除类别成功");
            }
        }else {//子类别
            List<VideoPO> videoPOS=videodao.queryCateVideoList(category.getId());
            if (videoPOS.size()!=0){
                //有视频
                map.put("message","此类别下存在视频,不可删除");
            }else {
                dao.deleteOneData(category);
                map.put("message","删除类别成功");
            }
        }
        return map;
    }

    @Override
    public String putPhoneCode(String phoneNum){
//        HashMap<String,Object> map=new HashMap<>();
        String code= ImageCodeUtil.getSecurityCode(6, ImageCodeUtil.SecurityCodeLevel.Simple,false);
        String signName="AGS课堂";
        String templateCode="SMS_205621621";
        String message=AliyunUtil.sendPhoneCode(phoneNum,signName,templateCode,code);

        return message;
    }

    @Override
    public List<CategoryPO> queryAllCategory() {
        List<CategoryPO> list=dao.selectFatherList();
        for (int i = 0; i < list.size(); i++) {
            CategoryPO category=list.get(i);//一个父类
            String fatherId= category.getId();
            List<CategoryPO> sonList= dao.selectSonListByFId(fatherId);
            category.setCategoryList(sonList);
        }
        return list;
    }
}
