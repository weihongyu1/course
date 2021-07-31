package com.why.qianyu.service;

import com.why.qianyu.entity.User;

/**
 * @Description TODO 用户业务接口
 * @Author why
 * @Date 2021/7/9 13:32
 * Version 1.0
 **/
public interface UserService {
    /**
     * 登录
     * @return
     */
    public boolean login(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    public boolean register(User user);
}
