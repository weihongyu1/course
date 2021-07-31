package com.why.qianyu.controller;

import com.why.qianyu.entity.Test01;
import com.why.qianyu.service.Test01Impl;
import com.why.qianyu.service.Test01Service;
import com.why.qianyu.util.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO 实例1控制类
 * @Author why
 * @Date 2021/7/8 11:13
 * Version 1.0
 **/
@Controller
public class Test01Controller {

    @Autowired
    Test01Impl test01;

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String get(String id,String pwd) throws Exception {
        String sql = "SELECT * FROM test01 WHERE id = "+id+" and pwd = "+pwd;
        Test01 test01 = JdbcUtil.queryForUser(sql);
        if (test01 != null){
            return "study/sqlInjectionStep/sucess";
        }
        return "study/sqlInjectionStep/fail";
    }

    @RequestMapping(value = "/solvetest02",method = RequestMethod.GET)
    public String test02(Model model,String id) throws Exception {
        String sql = "SELECT * FROM test01 WHERE id = "+id;
        Test01 test01 = JdbcUtil.queryForUser(sql);
        if (test01 != null){
            model.addAttribute("msg","正常执行");
        }else {
            model.addAttribute("msg","逻辑判断为假,SQL漏洞类型：数字型");
        }

        return "study/sqlInjectionStep/test02";
    }

    @RequestMapping(value = "/solvetest03",method = RequestMethod.GET)
    public String test03(Model model,String id) throws Exception {
        String sql = "SELECT * FROM test02 WHERE id = "+id;
        Test01 test01 = JdbcUtil.queryForUser(sql);
        if (test01 != null){
            model.addAttribute("msg","正常执行");
        }else {
            model.addAttribute("msg","逻辑判断为假,SQL漏洞类型：字符型");
        }

        return "study/sqlInjectionStep/test03";
    }
}
