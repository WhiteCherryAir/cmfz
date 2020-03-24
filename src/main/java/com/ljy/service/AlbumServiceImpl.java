package com.ljy.service;

import com.ljy.dao.AlbumDao;
import com.ljy.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public Album queryById(String id) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map queryByPageAlbum(Integer page, Integer rows) {
        //rows数据 page当前页 total总页数 records总条数
        Map map = new HashMap();
        int records = albumDao.selectCount(null);
        Integer total = records%rows==0 ? records/rows:records/rows+1;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows",albums);
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        return map;
    }

    @Override
    public Map insert(Album album) {
        Map map = new HashMap();
        String albumId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        album.setId(albumId);
        albumDao.insert(album);
        map.put("albumId",albumId);
        return map;
    }

    @Override
    public Map update(Album album) {
        Map map = new HashMap();
        albumDao.updateByPrimaryKeySelective(album);
        map.put("albumId",album.getId());
        return map;
    }

    @Override
    public Map deleteById(String[] id) {
        Map map = null;
        albumDao.deleteByIdList(Arrays.asList(id));
        return map;
    }
}
