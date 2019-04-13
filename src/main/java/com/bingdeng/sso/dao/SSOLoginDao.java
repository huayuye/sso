package com.bingdeng.sso.dao;

import com.bingdeng.sso.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by bingdeng on 2017/9/3.
 */
public interface SSOLoginDao {

    List<User> ssoLogin(@Param("userNo")String userNo, @Param("pwd")String pwd);
}
