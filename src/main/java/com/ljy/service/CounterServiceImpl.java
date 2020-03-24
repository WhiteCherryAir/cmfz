package com.ljy.service;

import com.ljy.dao.CounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Counter)表服务实现类
 *
 * @author makejava
 * @since 2020-01-06 19:46:39
 */
@Service("counterService")
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;


}