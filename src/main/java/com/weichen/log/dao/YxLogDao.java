package com.weichen.log.dao;

import com.weichen.log.entity.YxLog;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 15:45
 */
public interface YxLogDao {
    void insertOperation(YxLog log);
    void updateOperation(YxLog log);
    YxLog selectOneData(String id);
    List<YxLog> selectLogPageList(Integer startSize,Integer pageSize);
    Long findTotalCounts();
}
