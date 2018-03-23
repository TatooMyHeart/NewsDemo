package com.news.demo.service.impl;

import com.news.demo.dao.ArticleDao;
import com.news.demo.model.Article;
import com.news.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> getVerifyList(int page)throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        int start = (page-1)*10;
        articleList=articleDao.findVerifyList(start);
        return articleList;
    }

    @Override
    public int getCountNum()throws SQLException{
        int count = articleDao.getCountNum();
        return count;
    }

    @Override
    public List<Article> getBulletinList() throws SQLException {
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findBulletinList();
        return articleList;
    }

    //新闻公告
    @Override
    public List<Article> getNoticeList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findNoticeList();
        return articleList;
    }
    //精彩回顾
    @Override
    public List<Article> getReviewList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findReviewList();
        return articleList;
    }
    //活动预告
    @Override
    public List<Article> getForenoticeList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findForenoticeList();
        return articleList;
    }
    //成电辨坛
    @Override
    public List<Article> getDebateList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findDebateList();
        return articleList;
    }
    //成电舞台
    @Override
    public List<Article> getArtList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findArtList();
        return articleList;
    }
    //成电故事
    @Override
    public List<Article> getStoryList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findStoryList();
        return articleList;
    }
    //成电影院
    @Override
    public List<Article> getMovieList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findMovieList();
        return articleList;
    }
    //成电栋梁
    @Override
    public List<Article> getRidgepoleList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findRidgepoleList();
        return articleList;
    }
    //成电讲坛
    @Override
    public List<Article> getRostrumList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findRostrumList();
        return articleList;
    }
    //成电百家
    @Override
    public List<Article> getThinkerList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findThinkerList();
        return articleList;
    }
    //其他
    @Override
    public List<Article> getOtherList() throws SQLException{
        List<Article> articleList = new ArrayList<Article>();
        articleList=articleDao.findOtherList();
        return articleList;
    }

    @Override
    public Article getArticle(int id) throws SQLException{
        Article article = new Article();
        articleDao.addClick(id);
        article = articleDao.findArticleById(id);
        return article;
    }
    @Override
    public String saveArticle(Article article) throws SQLException{
        String result ="";
        Date utilDate=new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String time = df.format(utilDate);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        article.setSubmitAt(sqlDate);
        article.setNewsStatus("待审核");
        article.setClick(0);
        articleDao.insertArticle(article);
        return result;
    }

    @Override
    public String changeArticle(Article article) throws SQLException{
        String result ="";
        articleDao.updateArticle(article);
        return result;
    }


    @Override
    public String deleteArticle(int id) throws SQLException{
        String result ="";
        articleDao.deleteArticle(id);
        return result;
    }
}
