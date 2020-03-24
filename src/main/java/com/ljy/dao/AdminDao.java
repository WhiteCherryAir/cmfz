package com.ljy.dao;

import com.ljy.entity.Admin;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
@org.apache.ibatis.annotations.Mapper
public interface AdminDao extends Mapper<Admin>, DeleteByIdListMapper<Admin,String> {
    Admin queryAdminInfo(String username);
}
