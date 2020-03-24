package com.ljy.service;

import com.ljy.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (Course)表服务实现类
 *
 * @author makejava
 * @since 2020-01-06 16:31:29
 */
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

}