package com.weichen.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解:
 * 1.元注解 : 描述注解的注解 由 java原生提供
 * @Target 指定当前注解可以使用在那些位置
 *      ElementType.METHOD 使注解 可以应用在 方法
 *      ElementType.TYPE 使注解 可以应用在 类
 *      ElementType.PARAMETER 使注解 可以应用在方法参数
 * @Retention 指定注解的有效期
 *      RetentionPolicy.RUNTIME 代表在程序运行时有效
 * @Documented 指定注解是否出现在javadoc文档中
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YingxLog {
    /**
     * 对于 value 属性名 在使用的时候可以省略不写
     * 一个注解类中 只能有一个value属性名
     */
    String value() default "";
    String name() default "";
    String tableName() default "";
    String operation() default "";

}
