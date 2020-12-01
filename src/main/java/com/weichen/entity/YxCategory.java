package com.weichen.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-20
 * @changeTime 16:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class YxCategory implements Serializable {
    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;

}
