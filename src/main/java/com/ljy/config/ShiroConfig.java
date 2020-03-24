package com.ljy.config;

import com.ljy.realm.MyRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        //创建过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //过滤器链 拦截  不拦截
        //anon匿名过滤器 不认证     authc认证过滤器 认证
        HashMap hashMap = new HashMap();
        hashMap.put("/**","authc");
        hashMap.put("/admin/login","anon");
        hashMap.put("/admin/verify","anon");
        hashMap.put("/boot/**","anon");
        hashMap.put("/echarts/**","anon");
        hashMap.put("/img/**","anon");
        hashMap.put("/jqugrid/**","anon");
        hashMap.put("/kindeditor/**","anon");
        //配置登陆页面
        shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        //配置过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    //将安全管理器交给工厂管理
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        CacheManager cacheManager = new EhCacheManager();
        defaultWebSecurityManager.setCacheManager(cacheManager);
        //项目中安全管理器需要自定义数据源实现
        defaultWebSecurityManager.setRealm(myRealm());
        return defaultWebSecurityManager;
    }
    @Bean
    //将myrealm对象交给工厂管理
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        //自定义myrealm需要MD5凭证匹配器支持
        myRealm.setCredentialsMatcher(credentialsMatcher());
        return myRealm;
    }
    @Bean
    //将凭证匹配器交给工厂管理
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

}
