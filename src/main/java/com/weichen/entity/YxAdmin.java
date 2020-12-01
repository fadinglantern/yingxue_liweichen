package com.weichen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author weichenLi
 * @create 2020-11-19
 * @changeTime 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YxAdmin implements Serializable {
    private String id;
    private String username;
    private String password;
}
