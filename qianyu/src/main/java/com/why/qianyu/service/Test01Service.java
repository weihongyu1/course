package com.why.qianyu.service;

import com.why.qianyu.entity.Test01;

/**
 * @Description TODO 实例1业务类
 * @Author why
 * @Date 2021/7/8 11:10
 * Version 1.0
 **/
public interface Test01Service {
    /**
     * 根据用户id返回用户
     * @param id
     * @return
     */
    public Test01 getUser(String id);

    /**
     * 根据用户id返回Test02
     * @param id
     * @return
     */
    public String getTest02(String id);
}
