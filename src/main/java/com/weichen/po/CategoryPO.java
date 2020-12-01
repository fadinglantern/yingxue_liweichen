package com.weichen.po;

import com.weichen.entity.YxCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-25
 * @changeTime 20:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPO {
    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;
    private List<CategoryPO> categoryList;
}
