package com.news.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface ImgService {
    String uploadImg(MultipartFile file,String path);
    List<String> getVisibleCarousel() throws SQLException;
    List<String> getCarousel() throws SQLException;
    boolean changeVisible(String newPath,int nowPlace) throws SQLException;
    boolean uploadCarousel(String path) throws SQLException;
    boolean deleteCarousel(String path) throws SQLException;
}
