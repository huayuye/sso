package com.bingdeng.sso.service;

import com.bingdeng.sso.dao.SSOLoginDao;
import com.bingdeng.sso.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bingdeng on 2017/9/3.
 */
@Service
public class SSOLoginService {


    @Autowired
    private SSOLoginDao ssoLoginDao;
    public User ssoLogin(String userNo, String pwd) {
        List<User> list= ssoLoginDao.ssoLogin(userNo,pwd);
        return (list!=null&&list.size()>0)?list.get(0):null;
    }
}
