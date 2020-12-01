package com.weichen.service;

import com.weichen.dao.YxAdminDao;
import com.weichen.entity.YxAdmin;
import com.weichen.entity.YxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author weichenLi
 * @create 2020-11-19
 * @changeTime 17:33
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class YxAdminServiceImpl implements YxAdminService {
    @Autowired
    private YxAdminDao dao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public YxAdmin findAdmin(YxAdmin admin) {
        return dao.selectByNP(admin);
    }
}
