package com.ljy.dao;

import com.ljy.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * (Chapter)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-28 16:24:08
 */
@org.apache.ibatis.annotations.Mapper
public interface ChapterDao  extends Mapper<Chapter>, DeleteByIdListMapper<Chapter,String> {

}