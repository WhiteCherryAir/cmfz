package com.ljy.service;


import com.ljy.entity.Admin;

import java.util.Map;

public interface AdminService {
    Admin queryAdminByName(String name);
    //分页
    Map queryByPageAdmin(Integer page,Integer rows);
    Map insertAdmin(Admin admin);
    Map updateAdmin(Admin admin);
    Map deleteAdmin(String[] id);
}
