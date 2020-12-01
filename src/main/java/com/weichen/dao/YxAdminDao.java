package com.weichen.dao;

import com.weichen.entity.YxAdmin;
import com.weichen.entity.YxUser;

/**
 * @author weichenLi
 * @create 2020-11-19
 * @changeTime 17:30
 */
public interface YxAdminDao {
    YxAdmin selectByNP(YxAdmin admin);
}
