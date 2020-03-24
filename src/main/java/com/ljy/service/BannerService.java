package com.ljy.service;

import java.util.Map;


/**
 * (Banner)表服务接口
 *
 * @author makejava
 * @since 2019-12-26 11:36:53
 */
public interface BannerService {



    Map queryAllByLimit(Integer curPage, Integer pageSize);



}