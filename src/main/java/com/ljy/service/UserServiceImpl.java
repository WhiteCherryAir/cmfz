package com.ljy.service;

import com.ljy.dao.UserDao;
import com.ljy.entity.User;
import com.ljy.entity.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-01-03 21:13:06
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryUserByTime(String sex, Integer day) {
        return userDao.queryUserByTime(sex,day);
    }

    @Override
    public void insertUser(User user) {
        String userID = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        user.setId(userID);
        userDao.insert(user);
    }

    @Override
    public Map queryCountByLocation() {
        Map map = new HashMap();
        List<UserDTO> manList = userDao.queryCountByLocation("0");
        List<UserDTO> womenList = userDao.queryCountByLocation("1");

        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    @Override
    public User queryUserByPhone(String phone) {
       return userDao.queryUserByPhone(phone);
    }
}