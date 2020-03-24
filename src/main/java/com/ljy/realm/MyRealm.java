package com.ljy.realm;

import com.ljy.dao.AdminDao;
import com.ljy.entity.Admin;
import com.ljy.entity.Resource;
import com.ljy.entity.Role;
import com.ljy.util.MyWebWare;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
     //认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        //根据用户名从数据库查
        AdminDao adminDao = (AdminDao) MyWebWare.getBeanByClass(AdminDao.class);
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin adminByDB = adminDao.selectOne(admin);
        //封装AuthenticationInfo信息
        AuthenticationInfo authenticationInfo = new SimpleAccount(adminByDB.getUsername(), adminByDB.getPassword(), ByteSource.Util.bytes(adminByDB.getSalt()), this.getName());
        return authenticationInfo;
    }



    //doGetAuthorizationInfo获取授权信息方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份信息
        String username = (String) principalCollection.getPrimaryPrincipal();
        AdminDao adminDao = (AdminDao) MyWebWare.getBeanByClass(AdminDao.class);
        Admin admin = adminDao.queryAdminInfo(username);
        System.out.println(admin);
        //根据主身份信息查询数据库所拥有权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户身份信息
        List<Role> roles = admin.getRoles();
        //手动封装String类型集合
        ArrayList arrayList = new ArrayList();
        //手动封装权限信息String类型集合
        ArrayList arrayList2 = new ArrayList();
        for (Role role : roles) {
            arrayList.add(role.getRoleName());
            for (Resource resource : role.getResources()) {
                arrayList2.add(resource.getResourceName());
            }
        }
        simpleAuthorizationInfo.addRoles(arrayList);
        simpleAuthorizationInfo.addStringPermissions(arrayList2);
        if (username.equals("admin")) {
            simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //角色信息
            simpleAuthorizationInfo.addRole("admin");
            //权限信息
            //权限字符串  资源类型  操作类型  资源实列
            simpleAuthorizationInfo.addStringPermission("admin:*");
        }
        return simpleAuthorizationInfo;
    }
}