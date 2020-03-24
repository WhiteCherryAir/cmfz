package com.ljy.dao;

import com.ljy.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
@org.apache.ibatis.annotations.Mapper
public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {

}
