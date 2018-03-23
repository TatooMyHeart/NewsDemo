package com.news.demo.service.impl;

import com.news.demo.dao.PermissionDao;
import com.news.demo.dao.UserDao;
import com.news.demo.model.Permission;
import com.news.demo.model.User;
import com.news.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) throws SQLException
    {
        User user = userDao.findUserByUsername(username);
        return user;
    }

    @Override
    public boolean findAuthByUsername(String username,int auth) throws SQLException
    {
        User user = userDao.findUserByUsername(username);
        System.out.println("tempAuth的值为："+user.getAuthority());
        if(user.getAuthority()==auth){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String getCode(HttpServletRequest request) throws IOException {
        HttpSession session =request.getSession();
        int width = 80;
        int height = 40;
        int lines = 10;
        String code[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R"
                ,"S","T","U","V","W","X","Y","Z"};

        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,width,height);

        Date d = new Date();
        g.setFont(new Font("宋体",Font.BOLD,18));
        Random random = new Random(d.getTime());
        String validateCode = "";
        for(int i=0;i<4;i++)
        {
            int a = random.nextInt(20);
            int y = 10+random.nextInt(20);
            Color c = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            g.setColor(c);
            g.drawString(code[a],5+i*width/4,y);
            validateCode=validateCode+code[a];
        }

        session.setAttribute("code",validateCode);
        System.out.println("验证码是："+validateCode);
        for(int i=0;i<lines;i++)
        {
            Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            g.setColor(c);
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }
        g.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(img,"JPG",out);
            //System.out.println(out.toByteArray());
            byte[] bytes = out.toByteArray();
            String base64bytes = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
            String src = "data:image/jpg;base64,"+base64bytes;
            //System.out.println(src);
            out.close();
        return src;
    }

    @Override
    public String encrypt(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newPassword = base64Encoder.encode(md5.digest(password.getBytes()));
        return newPassword;
    }

    @Override
    public boolean checkUser(String username,String password)
    {
        Subject currentUser = SecurityUtils.getSubject();
        String encryptPassword = this.encrypt(password);
        System.out.println("加密后的密码："+encryptPassword);
        UsernamePasswordToken token = new UsernamePasswordToken(username, encryptPassword);
        try {
            currentUser.login(token);
            if (currentUser.isAuthenticated() == true) {
                return true;
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changePassword(String username,String password) throws SQLException{
        String encryptPassword = this.encrypt(password);
        userDao.updatePassword(username,encryptPassword);
        return true;
    }

    @Override
    public Permission getPermission(int pid) throws SQLException{
        Permission permission = permissionDao.findUrlByPid(pid);
        return permission;
    }
}
