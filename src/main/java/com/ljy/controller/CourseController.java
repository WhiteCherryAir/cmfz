package com.ljy.controller;

import com.ljy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Course)表控制层
 *
 * @author makejava
 * @since 2020-01-06 16:31:29
 */
@RestController
@RequestMapping("course")
public class CourseController {
    /**
     * 服务对象
     */
    @Autowired
    private CourseService courseService;



}