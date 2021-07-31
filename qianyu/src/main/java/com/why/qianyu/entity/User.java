package com.why.qianyu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 用户实体类
 * @Author why
 * @Date 2021/7/9 13:29
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String pwd;
    private String salt;
}
