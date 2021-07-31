package com.why.qianyu.dao;

import com.why.qianyu.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO 用户mapper
 * @Author why
 * @Date 2021/7/9 13:30
 * Version 1.0
 **/
@Mapper
public interface UserMapper {
    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    public User getUser(Integer id);

    /**
     * 插入数据
     */
    public void insertUser(User user);
}
