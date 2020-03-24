package com.ljy.service;

import com.ljy.entity.Article;

import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2019-12-30 20:13:41
 */
public interface ArticleService {
    Map ByPageArticle(Integer page, Integer rows);


    Map insert(Article article);


    Map update(Article article);


    Map deleteById(String[] id);
}