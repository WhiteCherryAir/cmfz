package com.ljy.dao;

import com.ljy.cache.MybatisCache;
import com.ljy.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
//mybatis注解式声明二级缓存
@CacheNamespace(implementation = MybatisCache.class)
@org.apache.ibatis.annotations.Mapper
public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner, String> {
    //一级页面
    List<Banner> queryBannersByStatus(String status);
}