package com.weichen.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author weichenLi
 * @create 2020-11-18
 * @changeTime 21:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YxUser implements Serializable {
    @Excel(name="ID", height = 20, width = 37)
    private String id;
    @Excel(name="昵称")
    private String nickName;
    @Excel(name="密码", width = 37)
    private String userPassword;
    @Excel(name="salt")
    private String salt;
    @Excel(name="电话" ,width=21)
    private String phone;
    @Excel(name="头像", width = 37)
    private String picImg;
    @Excel(name="简介")
    private String brief;
    @Excel(name="分数")
    private Double score;
    @Excel(name="生日",format = "yyyy年MM月dd日 HH:mm:ss",width = 30)
    private String createDate;
    @Excel(name="状态")
    private Integer state;
    @Excel(name="性别")
    private String sex;
    @Excel(name="城市")
    private String city;
}
