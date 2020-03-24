package com.ljy.dao;

import com.ljy.entity.Counter;
import tk.mybatis.mapper.common.Mapper;

/**
 * (Counter)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-06 19:45:47
 */
@org.apache.ibatis.annotations.Mapper
public interface CounterDao extends Mapper<Counter> {
    //根据id查计数器
    Counter queryCounterById(String counterId);
}