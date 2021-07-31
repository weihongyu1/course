package com.why.qianyu.service;

import com.why.qianyu.dao.Test01Mapper;
import com.why.qianyu.entity.Test01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author why
 * @Date 2021/7/8 11:11
 * Version 1.0
 **/
@Service
public class Test01Impl implements Test01Service {

    @Autowired
    Test01Mapper test01Mapper;

    @Override
    public Test01 getUser(String id) {
        return test01Mapper.getUser(id);
    }

    @Override
    public String getTest02(String id) {
        return test01Mapper.getTest02(id);
    }
}
