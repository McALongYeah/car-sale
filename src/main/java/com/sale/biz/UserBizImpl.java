package com.sale.biz;

import com.sale.bean.User;
import com.sale.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,//隔离级别,与数据库保持一致
        readOnly = false,
        rollbackFor = RuntimeException.class,
        timeout = 2000)
public class UserBizImpl implements UserBiz {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(int uid) {
        User user = null;
        user = userMapper.findById(uid);
        return user;
    }

    @Override
    public User findByName(String username, String pwd) {
        User user = null;
        user = userMapper.findByName(username,pwd);
        return user;
    }
}
