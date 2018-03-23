package com.news.demo.dao;

import com.news.demo.model.Img;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ImgDao {
    Img findPathById(int id) throws SQLException;
    //List<Img> findPathWithoutNow(int id) throws SQLException;
    List<Img> findAllPath() throws SQLException;
    Img findIdByPath(String path) throws SQLException;
    void insertImg(String path) throws SQLException;
    void deleteImgByPath(String path) throws SQLException;
}
