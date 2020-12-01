package com.weichen.dao;

import com.weichen.entity.MyCity;
import com.weichen.entity.MyDate;
import com.weichen.entity.YxUser;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-18
 * @changeTime 21:49
 */
public interface YxUserDao {
    List<YxUser> selectByPage(Integer startSize, Integer pageSize);
    List<YxUser> selectAll();
    YxUser selectOneData(String id);
    List<MyDate> selectSexCount(String sex);
    List<MyCity> selectSexCityCount(String sex);

    Long findTotalCounts();

    void updateUserState(YxUser user);
    void updateUser(YxUser user);
    void updatePicImg(YxUser user);

    void insertOneUser(YxUser user);

    void deleteOneData(YxUser user);

}
