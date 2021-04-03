package com.gps.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 注解的作用
 * 被此注解添加的方法 ，会被用于处理请求
 * 方法返回的内容，会直接重定向
 * */
public @interface ResponseView {
    String value();
}
