package com.ljy.service;

import com.ljy.entity.Album;

import java.util.Map;

/**
 * (Album)表服务接口
 *
 * @author makejava
 * @since 2019-12-27 17:49:18
 */
public interface AlbumService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Album queryById(String id);



    Map queryByPageAlbum(Integer page, Integer rows);

    /**
     * 新增数据
     *
     * @param album 实例对象
     * @return 实例对象
     */
    Map insert(Album album);

    /**
     * 修改数据
     *
     * @param album 实例对象
     * @return 实例对象
     */
    Map update(Album album);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Map deleteById(String[] id);

}