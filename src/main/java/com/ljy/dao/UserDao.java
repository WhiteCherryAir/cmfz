package com.ljy.dao;

import com.ljy.entity.User;
import com.ljy.entity.UserDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-03 21:13:06
 */
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {
    //注册趋势
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    //根据用户的性别 查所在地
    List<UserDTO> queryCountByLocation(String sex);
    //登录
    User queryUserByPhone(String phone);
    //随机获取五个人
    List<User> queryFiveUser();


}