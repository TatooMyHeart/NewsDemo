package com.news.demo.config;

import com.news.demo.model.Permission;
import com.news.demo.model.User;
import com.news.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm{
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
    {
        logger.info("##################Shiro+logIn##################");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        User user = new User();
        try {
            user = userService.findUserByUsername(usernamePasswordToken.getUsername());
            if(user!=null)
            {
                // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
                return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################Shiro+verify##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
//        System.out.println("loginName:"+loginName);
         User user = (User)principalCollection.getPrimaryPrincipal();
         if(user!=null){
             SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
             Permission permission = null;
             try {
                 permission = userService.getPermission(user.getAuthority());
                 System.out.println("permission查到了");
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             String urls = permission.getUrl();
             String[] urlList= urls.split(",");
             for(String s : urlList){
                 authorizationInfo.addStringPermission(s.toString());
             }
             if(user.getAuthority()==1){
                 authorizationInfo.addRole("admin");
             }else{
                 authorizationInfo.addRole("author");
             }
            return authorizationInfo;
         }
        return null;
    }


}
