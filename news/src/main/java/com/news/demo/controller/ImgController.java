package com.news.demo.controller;

import com.news.demo.model.ArticleImg;
import com.news.demo.service.ImgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ImgController {
    @Autowired
    private ImgService imgService;

    /**
     * 上传封面，先把图片保存到服务器，返回地址给前端
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadImg")
    public ArticleImg uploadCover(@RequestParam(value = "photo",required = false)MultipartFile file, HttpServletRequest request) {
        String path=request.getSession().getServletContext().getRealPath("upload");
//        System.out.println(file.getOriginalFilename());
        String result = imgService.uploadImg(file,path);
        ArticleImg articleImg = new ArticleImg();
        articleImg.setUrl(result);
        return articleImg;
    }

    /**
     * 加载滚图，返回当前在播的图片地址
     * @return
     */
    @RequestMapping(value = "/getVisibleCarousel")
    public List<String> getVisibleCarousel(HttpServletRequest request) {
        String path=request.getSession().getServletContext().getRealPath("carousel");
        //System.out.println("getCarousel路径是："+path);
        List<String> visibleList = new ArrayList<String>();
        try {
            visibleList = imgService.getVisibleCarousel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visibleList;
    }


    /**
     * 加载滚图，只需要返回,所有的图片地址
     * @return
     */
    @RequestMapping(value = "/getAllCarousel")
    @RequiresPermissions("/getAllCarousel")
    public List<String> getAllCarousel(HttpServletRequest request) {
        String path=request.getSession().getServletContext().getRealPath("carousel");
        //int nowPlace = Integer.parseInt(request.getParameter("nowPlace"));
        List<String> carouselList = new ArrayList<String>();
        try {
            carouselList = imgService.getCarousel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carouselList;
    }

    /**
     * 添加滚图，保存新图,返回新图地址
     *
     * @return
     */
    @RequestMapping(value = "/uploadCarousel")
    @RequiresPermissions("/uploadCarousel")
    public String uploadCarousel(@RequestParam(value = "photo",required = false)MultipartFile file, HttpServletRequest request) {
        String path=request.getSession().getServletContext().getRealPath("carousel");
//        System.out.println(file.getOriginalFilename());
        String result = imgService.uploadImg(file,path);
        if(!(result.equals("FILE_NOT_FOUND")|result.equals("UNKNOW_ERROR")|result.equals("EMPTY_FILE"))){
            boolean b = true;
            try {
                b = imgService.uploadCarousel(result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(b){return "SUCCESS";}else{return "FAIL";}
        }else {
            return "FAIL";
        }
    }


    /**
     * 更换滚图在播情况，传入旧图地址，新图地址
     *
     * @return
     */
    @RequestMapping(value = "/changeVisible")
    @RequiresPermissions("/changeVisible")
    public String changeVisible(HttpServletRequest request) {
        String newPath=request.getParameter("newPath").toString();
        int nowPlace = Integer.parseInt(request.getParameter("nowPlace"));
        boolean b = true;
        try {
            b = imgService.changeVisible(newPath,nowPlace);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(b){return "SUCCESS";}else{return "FAIL";}
    }

    /**
     * 删除滚图，传入旧图地址，删掉旧图
     *
     * @return
     */
    @RequestMapping(value = "/deleteCarousel")
    @RequiresPermissions("/deleteCarousel")
    public String deleteCarousel(HttpServletRequest request) {
        String oldPath=request.getParameter("oldPath").toString();
        boolean b =true;
        try {
            b = imgService.deleteCarousel(oldPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(b){return "SUCCESS";}else{return "FAIL";}
    }
}
