package com.ljy.service;

import com.ljy.dao.ArticleDao;
import com.ljy.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2019-12-30 20:13:41
 */
@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    //分页
    public Map ByPageArticle(Integer page, Integer rows) {
        Map map = new HashMap();
        //得到总条数
        int records = articleDao.selectCount(null);
        //计算总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        //分页
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",articles);
        return map;

    }

    @Override
    public Map insert(Article article) {
        Map map = new HashMap();
        String articleId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        article.setId(articleId);
        Date date = new Date();
        article.setCreateDate(date);
        article.setPublishDate(date);
        articleDao.insert(article);
        map.put("articleId",articleId);
        return map;
    }

    @Override
    public Map update(Article article) {
        Map map = new HashMap();
        articleDao.updateByPrimaryKeySelective(article);
        map.put("articleId",article.getId());
        return map;
    }

    @Override
    public Map deleteById(String[] id) {
        Map map = null;
        articleDao.deleteByIdList(Arrays.asList(id));
        return map;
    }
}