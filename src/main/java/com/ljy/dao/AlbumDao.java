package com.ljy.dao;

import com.ljy.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * (Album)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-27 17:13:05
 */
@org.apache.ibatis.annotations.Mapper
public interface AlbumDao extends Mapper<Album>, DeleteByIdListMapper<Album,String> {

}