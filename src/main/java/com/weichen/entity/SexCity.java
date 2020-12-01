package com.weichen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weichenLi
 * @create 2020-11-29
 * @changeTime 18:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SexCity {
    private String sex;
    private List<MyCity> citys;
}
