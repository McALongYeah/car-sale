package com.sale.dao;

import com.sale.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where uid = #{uid}")
    public User findById(int uid);

    @Select("select pwd from user where username = #{username}")
    public User findByName(String username,String pwd);
}
