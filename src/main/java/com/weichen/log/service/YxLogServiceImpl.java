package com.weichen.log.service;

import com.weichen.entity.YxCategory;
import com.weichen.log.dao.YxLogDao;
import com.weichen.log.entity.YxLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 15:44
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class YxLogServiceImpl implements YxLogService{
    @Resource
    private YxLogDao dao;
    @Override
    public void addOperation(YxLog log,String operation) {
        dao.insertOperation(log);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> seleteByPage(Integer page, Integer rows) {
        HashMap<String,Object> map= new HashMap<>();
        List<YxLog> logs=dao.selectLogPageList((page-1)*rows, rows);
        Long totalCounts = dao.findTotalCounts();
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        System.out.println(totalCounts);
        System.out.println(totalPage);
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",logs);
        return map;
    }
}
