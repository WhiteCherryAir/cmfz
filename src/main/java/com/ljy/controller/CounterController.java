package com.ljy.controller;

import com.ljy.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Counter)表控制层
 *
 * @author makejava
 * @since 2020-01-06 19:45:49
 */
@RestController
@RequestMapping("counter")
public class CounterController {
    /**
     * 服务对象
     */
    @Autowired
    private CounterService counterService;



}