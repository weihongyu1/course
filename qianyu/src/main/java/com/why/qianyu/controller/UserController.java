package com.why.qianyu.controller;

import com.why.qianyu.entity.User;
import com.why.qianyu.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.net.idn.Punycode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description TODO 登录注册
 * @Author why
 * @Date 2021/7/9 13:46
 * Version 1.0
 **/
@Controller
public class UserController {

    @RequestMapping("/show/login")
    public String showLogin(){
        return "user/signIn";
    }

    @RequestMapping("/show/register")
    public String showRegister(){
        return "user/register";
    }

    @Autowired
    UserServiceImpl userService;

    /**
    * 用户登录接口
     * 登录成功返回课程界面
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String id = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        HttpSession session = request.getSession();
        session.setAttribute("login",id);
        User user = new User(Integer.valueOf(id), pwd, null);
        boolean login = userService.login(user);
        if (login){
            return "study/courses";
        }
        return "user/signIn";
    }

    /**
    * 用户注册接口
     * 注册成功返回登录界面
     */
    @RequestMapping("/register")
    public String register(HttpServletRequest request){
        String id = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        User user = new User(Integer.valueOf(id), pwd, null);
        boolean register = userService.register(user);
        if (register){
            return "user/signIn";
        }
        return "user/register";
    }

    @RequestMapping("/index")
    public String showIndex(){
        return "user/index";
    }
}
