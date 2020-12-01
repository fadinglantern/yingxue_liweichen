package com.weichen.log.service;

import com.weichen.log.entity.YxLog;

import java.util.HashMap;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 15:44
 */
public interface YxLogService {
    void addOperation(YxLog log,String operation);
    HashMap<String,Object> seleteByPage(Integer page, Integer rows);
}
