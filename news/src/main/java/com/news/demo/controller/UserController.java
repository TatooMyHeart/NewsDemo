package com.news.demo.controller;

import com.news.demo.model.User;
import com.news.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/logIn")
    public String login(@RequestBody User user,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String code = "";
        if(session.getAttribute("code").toString().equals("")){
            System.out.println("没有code");
            return "ERROR_CODE";
        }else{
            code=session.getAttribute("code").toString();
        }
        try {

            if(!userService.findAuthByUsername(user.getUsername(),user.getAuthority())){
                return "AUTH_ERROR";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user.getCode().toUpperCase().equals(code)) {
            if(userService.checkUser(user.getUsername(),user.getPassword())) {
                session.setAttribute("username",user.getUsername());
                return "SUCCESS";
            }else {
                return "USERNAME_OR_PASSWORD_ERROR";
            }
        }else
        {
            return "ERROR_CODE";
        }
    }

    @RequestMapping(value = "/getCode")
    public String getCode(HttpServletRequest request)
    {
        String s = "";
        try {
            s = userService.getCode(request);
        } catch (IOException e)   {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping(value = "/changePassword")
    @RequiresPermissions("/changePassword")
    public String changePassword(HttpServletRequest request)
    {
            try {
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String username = request.getSession().getAttribute("username").toString();
                if(userService.checkUser(username,oldPassword)) {
                    if (userService.changePassword(username, newPassword)) {
                            return "SUCCESS";
                    }else {
                        return "FAIL";
                    }
                }else{
                    return "OLD_PASSWORD_ERROR";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "ERROR";
            }
    }

    @RequestMapping(value = "/unLogIn")
    public String error1()
    {
        return "请先登录";
    }

    @RequestMapping(value = "/unAuthorized")
    public String error2()
    {
        return "您没有访问权限";
    }



}
