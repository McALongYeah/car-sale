package com.sale.web.Controller;

import com.sale.bean.User;
import com.sale.biz.UserBiz;
import com.sale.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserBiz userBiz;

    @RequestMapping("findById")
    public Map<String,Object> findById(@Param("uid")int uid){
        Map<String,Object> map = new HashMap<>();
        User user = null;
        try{
            user = this.userBiz.findById(uid);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("msg","在获取数据时发生异常!");
        }
        map.put("code",1);
        map.put("data",user);
        return map;
    }

    @RequestMapping(value = "login",method = {GET,POST})
    public Map<String,Object> login(User user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");

        if (StringUtils.empty(user.getUsername() )||StringUtils.empty (user.getPwd())){
            map.put("code",-2);
            map.put("msg","用户名或密码为空");
            return map;
        }
        //处理密码: 用md5 加密
        String md5pass = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        //访问业务层 login
        User ur = userBiz.findByName(user.getUsername(),md5pass);
        if (ur == null){
            //失败 则code = 0
            map.put("code",-3);
            map.put("msg","用户名或密码错误");
            return map;
        }
        //成功，则code = 1
        map.put("code",1);
        //返回一个数据给客户端
        ur.setPwd("");
        //在session中保存用户信息，以维持登录状态
        session.setAttribute("user",ur);

        map.put("data",ur);
        return map;
    }
}
