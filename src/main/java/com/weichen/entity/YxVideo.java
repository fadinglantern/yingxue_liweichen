package com.weichen.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author weichenLi
 * @create 2020-11-23
 * @changeTime 22:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class YxVideo implements Serializable {
    private String id;
    private String title;
    private String brief;
    private String coverPath;
    private String videoPath;
    private String uploadTime;
    private Integer likeCount;
    private Integer playCount;
    private String cateName;
    private String categoryId;
    private String userId;
    private String groupId;
    private Integer state;
}
