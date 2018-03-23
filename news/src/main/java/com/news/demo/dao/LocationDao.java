package com.news.demo.dao;

import com.news.demo.model.Location;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface LocationDao {
    List<Location> findIdByImgid(int imgid) throws SQLException;
    List<Location> findOrderList() throws SQLException;
    Location findImgidById(int id) throws SQLException;
    void updateImgidById(@Param("imgid")int imgid,@Param("id")int id) throws SQLException;
}
