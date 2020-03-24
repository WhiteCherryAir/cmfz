package com.ljy.controller;

import com.ljy.dao.UserDao;
import com.ljy.entity.User;
import com.ljy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-01-03 21:13:06
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     *
     */
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @RequestMapping("showUserByTime")
    public Map showUserByTime() {
        Map map = new HashMap();
        List manList = new ArrayList();
        manList.add(userService.queryUserByTime("0",1));
        manList.add(userService.queryUserByTime("0",7));
        manList.add(userService.queryUserByTime("0",30));
        manList.add(userService.queryUserByTime("0",365));
        List womenList = new ArrayList();
        womenList.add(userService.queryUserByTime("1",1));
        womenList.add(userService.queryUserByTime("1",7));
        womenList.add(userService.queryUserByTime("1",30));
        womenList.add(userService.queryUserByTime("1",365));
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    @RequestMapping("insertUser")
    public void insertUser(User user){
        String userId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        user.setId(userId);
        user.setLocation("四川");
        user.setRigestDate(new Date());
        userDao.insert(user);

    }

    //用户所在地分布
    @RequestMapping("showUserLocation")
    public Map showUserLocation(){
        Map map = userService.queryCountByLocation();
        return map;
    }

}