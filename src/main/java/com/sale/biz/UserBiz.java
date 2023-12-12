package com.sale.biz;

import com.sale.bean.User;

import java.util.Map;

public interface UserBiz {
    public User findById(int uid);
    public User findByName(String username,String pwd);
}
