package com.news.demo.dao;

import com.news.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface UserDao {
   User findUserByUsername(String username) throws SQLException;
   void updatePassword(@Param("username") String username, @Param("password") String passsword) throws SQLException;
}
