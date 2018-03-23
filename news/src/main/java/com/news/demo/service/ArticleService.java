package com.news.demo.service;

import com.news.demo.model.Article;

import java.sql.SQLException;
import java.util.List;

public interface ArticleService {
    //审核列表
    List<Article> getVerifyList(int page) throws SQLException;
    //新闻动态列表
    List<Article> getBulletinList() throws SQLException;
    //新闻公告
    List<Article> getNoticeList() throws SQLException;
    //精彩回顾
    List<Article> getReviewList() throws SQLException;
    //活动预告
    List<Article> getForenoticeList() throws SQLException;
    //成电辨坛
    List<Article> getDebateList() throws SQLException;
    //成电舞台
    List<Article> getArtList() throws SQLException;
    //成电故事
    List<Article> getStoryList() throws SQLException;
    //成电影院
    List<Article> getMovieList() throws SQLException;
    //成电栋梁
    List<Article> getRidgepoleList() throws SQLException;
    //成电讲坛
    List<Article> getRostrumList() throws SQLException;
    //成电百家
    List<Article> getThinkerList() throws SQLException;
    //其他
    List<Article> getOtherList() throws SQLException;

    Article getArticle(int id) throws SQLException;
    String saveArticle(Article article) throws SQLException;
    String changeArticle(Article article) throws SQLException;
    String deleteArticle(int id) throws SQLException;
    int getCountNum() throws SQLException;
}
