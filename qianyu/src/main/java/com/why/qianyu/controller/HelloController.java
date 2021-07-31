package com.why.qianyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author why
 * @Date 2021/7/6 13:47
 * Version 1.0
 **/
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "study/sqlInjectionStep/hello";
    }

    /**
    * 请求课程页面接口
     * @return 返回课程页面
     */
    @RequestMapping("/courses")
    public String courses(HttpServletRequest request, Model model){
        Object login = request.getSession().getAttribute("login");
        model.addAttribute("login",login);
        if (login == null){
            return "user/signIn";
        }
        return "study/courses";
    }

    /**
    * 请求学习页面接口
     */
    @RequestMapping("/sqlInjection")
    public String sqlInjection(HttpServletRequest request,Model model){
        Object login = request.getSession().getAttribute("login");
        if (login == null){
            return "user/signIn";
        }
        model.addAttribute("login",login);
        return "study/sqlInjectionStep/sqlInjection";
    }

    @RequestMapping("/sqlInjectionStep1")
    public String sqlInjectionstep1(){
        return "study/sqlInjectionStep/sqlStep1";
    }

    @RequestMapping("/test01")
    public String test01(){
        return "study/sqlInjectionStep/test01";
    }

    @RequestMapping("/test02")
    public String test02(){
        return "study/sqlInjectionStep/test02";
    }

    @RequestMapping("/test03")
    public String test03(){
        return "study/sqlInjectionStep/test03";
    }
}
