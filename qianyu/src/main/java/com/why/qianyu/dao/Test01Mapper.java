package com.why.qianyu.dao;

import com.why.qianyu.entity.Test01;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO test01数据库类
 * @Author why
 * @Date 2021/7/8 10:51
 * Version 1.0
 **/
@Mapper
public interface Test01Mapper {

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    public Test01 getUser(String id);

    /**
     * 根据用户id查询test02
     * @param id
     * @return
     */
    public String getTest02(String id);
}
