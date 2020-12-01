package com.weichen.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weichenLi
 * @create 2020-11-25
 * @changeTime 18:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPO {
    private String id;
    private String videoTitle;
    private String description;
    private String cover;
    private String path;
    private String uploadTime;
    private Integer likeCount;

    private String cateName;
    private String userPhoto;

    private String categoryId;
    private String userId;
    private String username;

}
