package com.ljy.dao;

import com.ljy.entity.Course;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Course)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-06 16:31:29
 */
@org.apache.ibatis.annotations.Mapper
public interface CourseDao extends Mapper<Course> {
    //根据用户Id展示功课
    List<Course> queryCourseByUserId(String userId);
    //根据id查功课
    Course queryCourseById(String courseId);
}