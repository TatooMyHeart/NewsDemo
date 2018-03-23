package com.news.demo.dao;

import com.news.demo.model.Article;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ArticleDao {
    void insertArticle(Article article) throws SQLException;
    void updateArticle(Article article) throws SQLException;
    void addClick(int id) throws SQLException;
    void deleteArticle(int id) throws SQLException;
    int getCountNum() throws SQLException;
    Article findArticleById(int id) throws SQLException;
    //审核列表
    List<Article> findVerifyList(int start) throws SQLException;
    //新闻动态列表
    List<Article> findBulletinList() throws SQLException;
    //新闻公告
    List<Article> findNoticeList() throws SQLException;
    //精彩回顾
    List<Article> findReviewList() throws SQLException;
    //活动预告
    List<Article> findForenoticeList() throws SQLException;
    //成电辨坛
    List<Article> findDebateList() throws SQLException;
    //成电舞台
    List<Article> findArtList() throws SQLException;
    //成电故事
    List<Article> findStoryList() throws SQLException;
    //成电影院
    List<Article> findMovieList() throws SQLException;
    //成电栋梁
    List<Article> findRidgepoleList() throws SQLException;
    //成电讲坛
    List<Article> findRostrumList() throws SQLException;
    //成电百家
    List<Article> findThinkerList() throws SQLException;
    //其他
    List<Article> findOtherList() throws SQLException;
}
