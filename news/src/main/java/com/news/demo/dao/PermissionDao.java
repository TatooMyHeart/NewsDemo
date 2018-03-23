package com.news.demo.dao;

import com.news.demo.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao {
    Permission findUrlByPid(int id);
}
