package com.ljy.service;

import com.ljy.dao.ChapterDao;
import com.ljy.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * (Chapter)表服务实现类
 *
 * @author makejava
 * @since 2019-12-28 16:24:08
 */
@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    //分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map ByPageChapter(String albumId, Integer page, Integer rows) {
        Map map = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        Integer i = chapterDao.selectCount(chapter);
        Integer total = i % rows == 0 ? i / rows : i / rows + 1;
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        map.put("records",i);
        map.put("page",page);
        map.put("total",total);
        map.put("rows",chapters);
        return map;

    }

    /**
     * 新增数据
     *
     * @param chapter 实例对象
     * @return 实例对象
     */
    @Override
    public Map insert(Chapter chapter,String albumId) {
        Map map = new HashMap();
        String chapterId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        chapter.setId(chapterId);
        chapter.setAlbumId(albumId);
        chapterDao.insert(chapter);
        map.put("chapterId", chapterId);
        return map;
    }

    /**
     * 修改数据
     *
     * @param chapter 实例对象
     * @return 实例对象
     */
    @Override
    public Map update(Chapter chapter) {
        Map map = new HashMap();
        chapterDao.updateByPrimaryKeySelective(chapter);
        map.put("chapterId", chapter.getId());
        return map;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Map deleteById(String[] id) {
        Map map = null;
        chapterDao.deleteByIdList(Arrays.asList(id));
        return map;
    }
}