package com.news.demo.service.impl;

import com.news.demo.dao.ImgDao;
import com.news.demo.dao.LocationDao;
import com.news.demo.model.Img;
import com.news.demo.model.Location;
import com.news.demo.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ImgServiceImpl implements ImgService{

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private ImgDao imgDao;

    @Override
    public String uploadImg(MultipartFile file, String path) {
        if(!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                String realPath=path+"/"+ UUID.randomUUID()+"."+suffix;
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(realPath)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return realPath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "FILE_NOT_FOUND";
            } catch (IOException e) {
                e.printStackTrace();
                return "UNKNOW_ERROR";
            }
        }else {
            return "EMPTY_FILE";
        }
    }

    @Override
    public List<String> getVisibleCarousel() throws SQLException {
        //获取根据位置排好序的在播轮播图的id
        List<Location> orderList = locationDao.findOrderList();
        List<String> visibleList = new ArrayList<String>();
        for(int i=0;i<orderList.size();i++){
            if (orderList.get(i).getImgid()==0) {
                visibleList.add("");
            }else{
                Img img= imgDao.findPathById(orderList.get(i).getImgid());
                visibleList.add(img.getPath());
            }
        }
        return visibleList;
    }


        @Override
    public List<String> getCarousel() throws SQLException {
        List<String> carouselList = new ArrayList<String>();
        List<Img>  imgList = imgDao.findAllPath();

        for(Img i:imgList){
            carouselList.add(i.getPath());
        }
        return carouselList;
    }

    @Override
    public boolean changeVisible(String newPath,int nowPlace) throws SQLException {
        Img img = imgDao.findIdByPath(newPath);
        locationDao.updateImgidById(img.getId(),nowPlace);
        return true;
    }

    @Override
    public boolean uploadCarousel(String path) throws SQLException {
        imgDao.insertImg(path);
        return true;
    }

    @Override
    public boolean deleteCarousel(String path) throws SQLException {
        File oldFile = new File(path);
        if(oldFile.exists()){
            oldFile.delete();
            Img img = imgDao.findIdByPath(path);
            imgDao.deleteImgByPath(path);
            List<Location> locations= locationDao.findIdByImgid(img.getId());
            if(locations==null){
                return true;
            }else{
                for(Location l:locations){
                    locationDao.updateImgidById(0,l.getId());
                }
                return true;
            }
        }
        return false;
    }
}
