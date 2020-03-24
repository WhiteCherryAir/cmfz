package com.ljy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.ljy.annotation.AddorSelectCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint) {
        //缓存数据结构 Key原始类类名全限定名 key方法名+参数 value数据
        String clazz = proceedingJoinPoint.getTarget().getClass().toString();
        System.out.println(clazz);
        String name = proceedingJoinPoint.getSignature().getName();

        Object[] args = proceedingJoinPoint.getArgs();
        String key = name;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            key+=arg;
        }
        //先查询缓存数据然后查询数据库数据
        Object o = redisTemplate.opsForHash().get(clazz, key);
        //有缓存直接返回数据
        if(o!=null){
            return o;
        }
        //没有缓存直接访问数据库，数据添加到缓存,返回数据
        try {
            //proceed 切入方法的返回值
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForHash().put(clazz,key,proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }

    }
}
