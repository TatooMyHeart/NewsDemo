package com.news.demo.controller;

import com.news.demo.model.Article;
import com.news.demo.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController{

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    /**
     * 获取审核列表，包含标题、作者、提交日期、审核状态,文章id
     * @return
     */
    @RequestMapping(value = "/verify")
    @RequiresPermissions("/verify")
    public Map verify(HttpServletRequest request)
    {
        int page = Integer.parseInt(request.getParameter("page").toString());
        List<Article> articleList = new ArrayList<Article>();
        Map map = new HashMap();
        int count = 0;
        try {
             articleList = articleService.getVerifyList(page);
             count = articleService.getCountNum();
        } catch (SQLException e) {
            logger.info("################获取审核列表失败############################");
            e.printStackTrace();
        }
        map.put("articles",articleList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取新闻动态列表，按最新发布排序，展示四个
     * @return
     */
    @RequestMapping(value = "/getBulletin")
    public List<Article> getBulletin()
    {
        List<Article> articleList = new ArrayList<Article>();
        try {
            articleList = articleService.getBulletinList();
        } catch (SQLException e) {
            logger.info("################获取新闻动态列表失败############################");
            e.printStackTrace();
        }
        return articleList;
    }

    /**
     * 速报列表，返回全部三个类型八个对象，每种十个，按照发布日期排序
     * @return
     */
    @RequestMapping(value = "/getNotice")
    public List<List<Article>> getNotice()
    {
        List<List<Article>> articleLists = new ArrayList<List<Article>>();
        List<Article> articleList1 = new ArrayList<Article>();List<Article> articleList2 = new ArrayList<Article>();List<Article> articleList3 = new ArrayList<Article>();
        List<Article> articleList4 = new ArrayList<Article>();List<Article> articleList5 = new ArrayList<Article>();List<Article> articleList6 = new ArrayList<Article>();
        List<Article> articleList7 = new ArrayList<Article>();List<Article> articleList8 = new ArrayList<Article>();List<Article> articleList9 = new ArrayList<Article>();
        List<Article> articleList10 = new ArrayList<Article>();List<Article> articleList11 = new ArrayList<Article>();
        try {
            articleList1=articleService.getNoticeList();
            articleList2=articleService.getReviewList();
            articleList3=articleService.getForenoticeList();
            articleList4=articleService.getDebateList();
            articleList5=articleService.getArtList();
            articleList6=articleService.getStoryList();
            articleList7=articleService.getMovieList();
            articleList8=articleService.getRidgepoleList();
            articleList9=articleService.getRostrumList();
            articleList10=articleService.getThinkerList();
            articleList11=articleService.getOtherList();
            articleLists.add(articleList1);
            articleLists.add(articleList2);
            articleLists.add(articleList3);
            articleLists.add(articleList4);
            articleLists.add(articleList5);
            articleLists.add(articleList6);
            articleLists.add(articleList7);
            articleLists.add(articleList8);
            articleLists.add(articleList9);
            articleLists.add(articleList10);
            articleLists.add(articleList11);
        } catch (SQLException e) {
            logger.info("################获取速报列表失败############################");
            e.printStackTrace();
        }
        return articleLists;
    }

    @RequestMapping(value = "/getArticle")
    public Article getArticle(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id").toString());
        Article article = new Article();
        try {
            article=articleService.getArticle(id);
        } catch (SQLException e) {
            logger.info("################获取新闻失败############################");
            e.printStackTrace();
        }
        return article;
    }

    @RequestMapping(value = "/saveArticle")
    public String saveArticle(@RequestBody Article article)
    {
        String result="";
        try {
            result=articleService.saveArticle(article);
        } catch (SQLException e) {
            logger.info("################保存新闻失败############################");
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    /**
     * 要么投稿人投稿，即保存文章，要么管理员打开审核，编辑后选择通过或者不通过
     * @param article
     * @return
     */
    @RequestMapping(value = "/changeArticle")
    @RequiresPermissions("/changeArticle")
    public String changeArticle(@RequestBody Article article){
        String result="";
        try {
            result=articleService.changeArticle(article);
        } catch (SQLException e) {
            logger.info("################修改新闻失败############################");
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/deleteArticle")
    @RequiresPermissions("/deleteArticle")
    public String deleteArticle(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String result="";
        try {
            result=articleService.deleteArticle(id);
        } catch (SQLException e) {
            logger.info("################删除新闻失败############################");
            e.printStackTrace();
        }
        return "SUCCESS";
    }

}
