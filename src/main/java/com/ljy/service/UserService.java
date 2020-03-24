package com.ljy.service;

import com.ljy.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-01-03 21:13:06
 */
public interface UserService {
    //查询用户注册趋势
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    //添加用户
    void insertUser(User user);
    //查用户地区分布
    Map queryCountByLocation();
    //登录
    User queryUserByPhone(String phone);

}