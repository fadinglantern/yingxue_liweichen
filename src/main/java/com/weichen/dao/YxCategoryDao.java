package com.weichen.dao;

import com.weichen.entity.YxCategory;
import com.weichen.entity.YxUser;
import com.weichen.po.CategoryPO;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-20
 * @changeTime 16:47
 */
public interface YxCategoryDao {
    List<YxCategory> selectFatherPageList(Integer startSize, Integer pageSize);
    List<YxCategory> selectSonPageListByFId(Integer levels,String parentId,Integer startSize, Integer pageSize);
    //分类查询 app
    List<CategoryPO> selectFatherList();
    List<CategoryPO> selectSonListByFId(String id);
    Long findTotalCounts(YxCategory category);

    YxCategory selectOneData(String id);
    void insertOneData(YxCategory category);
    void updateOneData(YxCategory category);
    void deleteOneData(YxCategory category);
}
