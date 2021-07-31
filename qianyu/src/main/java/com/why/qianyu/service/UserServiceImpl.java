package com.why.qianyu.service;

import com.why.qianyu.dao.UserMapper;
import com.why.qianyu.entity.User;
import com.why.qianyu.util.SaltUtil;
import com.why.qianyu.util.Sm3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO UserService实现类
 * @Author why
 * @Date 2021/7/9 13:35
 * Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
    * 登录设计
     * @param user 用户信息
     */
    @Override
    public boolean login(User user) {
        User user1 = null;
        if (user.getId() != null){
            user1 = userMapper.getUser(user.getId());
        }
        //加盐加密
        String merge = SaltUtil.merge(user.getPwd(), user1.getSalt());
        System.out.println(merge);
        if (merge.equals(user1.getPwd())){
            return true;
        }

        return false;
    }

    /**
    * 注册设计
     * @param user 用户注册信息
     */
    @Override
    public boolean register(User user) {
        String salt = Sm3Util.creatSalt();
        String merge = SaltUtil.merge(user.getPwd(), salt);
        user.setPwd(merge);
        user.setSalt(salt);
        userMapper.insertUser(user);
        return true;
    }
}
