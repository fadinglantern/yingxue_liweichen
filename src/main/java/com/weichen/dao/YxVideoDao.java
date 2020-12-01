package com.weichen.dao;

import com.weichen.entity.YxUser;
import com.weichen.entity.YxVideo;
import com.weichen.po.CateVideoPO;
import com.weichen.po.VideoPO;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-23
 * @changeTime 22:08
 */
public interface YxVideoDao {
    List<YxVideo> selectVideoPageList(Integer startSize, Integer pageSize);
    YxVideo selectOneVideo(String id);
    Long findTotalCounts();
    void insertOneVideo(YxVideo video);
    void updateVideoPath(YxVideo video);
    void updateVideoState(YxVideo video);
    void deleteOneVideo(String id);
    void updateVideoData(YxVideo video);

    List<VideoPO> queryByReleaseTime();
    List<VideoPO> queryCateVideoList(String categoryId);
    List<VideoPO> queryByLikeVideoName(String content);

}
