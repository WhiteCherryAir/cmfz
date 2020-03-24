package com.ljy.cache;

import com.ljy.util.MyWebWare;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatisCache implements Cache {
    //必须有一个Id属性
    private final String id;
    //必须有一个id属性的有参构造
    public MybatisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
    //添加缓存
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");

        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }
    //取数据
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    //删除缓存
    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        redisTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
