package com.weichen.service;

import com.weichen.entity.SexCity;
import com.weichen.entity.YxUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-18
 * @changeTime 22:01
 */
public interface YxUserService {
    HashMap<String,Object> findByPage(Integer page, Integer rows);
    String pOIExpdpUser();
    String ePOIExpdpUser();
    HashMap<String,Object> getSexCounts();
    List<SexCity> getSexCityCounts();
    Long findTotalCounts();
    String addUser(YxUser user);
    String modifyOneUser(YxUser user);
    void deleteOneUser(YxUser user);
    void photoUpload(MultipartFile picName, String id);
    void photoUploadAliyun(MultipartFile picName, String id);
    void modifyState(YxUser user);
}
