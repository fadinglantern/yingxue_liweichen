package com.weichen.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YxLog implements Serializable {
    private String id;//日志编号
    private String username;//操作用户名
    private String operationTime;//操作时间
    private String tableName;//操作的表名
    private String operationMethod;//操作的业务类型
    private String methodName;//操作的方法签名
    private String dataId;//操作数据的ID
    private String dataInfo;//如果删除 记录删除的数据用来恢复
    private Double version;//版本
}
