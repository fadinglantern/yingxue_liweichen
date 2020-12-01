package com.weichen.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.weichen.entity.YxCategory;
import com.weichen.po.CategoryPO;

import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-20
 * @changeTime 17:01
 */
public interface YxCategoryService {
    HashMap<String,Object> getFatherPageList(Integer page, Integer rows);
    HashMap<String,Object> getSonPageListByFId(YxCategory category,String cateId,Integer page, Integer rows);
    Long findTotalCounts(YxCategory category);
//    YxCategory getOneData(String id);
    void saveOneData(YxCategory category);
    void modifyOneData(YxCategory category);
    HashMap<String,Object> deleteOneData(YxCategory category);

    String putPhoneCode(String phoneNum) throws ClientException;

    List<CategoryPO> queryAllCategory();
}
