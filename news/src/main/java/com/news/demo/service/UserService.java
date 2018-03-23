package com.news.demo.service;

import com.news.demo.model.Permission;
import com.news.demo.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    String getCode(HttpServletRequest request) throws IOException;
    User findUserByUsername(String username) throws SQLException;
    String encrypt(String password);
    boolean checkUser(String username,String password);
    boolean changePassword(String username,String password) throws SQLException;
    Permission getPermission(int pid) throws SQLException;
    boolean findAuthByUsername(String username,int auth) throws SQLException;
}
