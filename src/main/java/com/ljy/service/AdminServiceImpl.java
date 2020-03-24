package com.ljy.service;

import com.ljy.dao.AdminDao;
import com.ljy.entity.Admin;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
   private AdminDao adminDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin queryAdminByName(String name) {
        Admin admin = adminDao.selectOne(new Admin());
        return admin;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPageAdmin(Integer page, Integer rows) {
        Map map = new HashMap();
        Integer i = adminDao.selectCount(null);
        Integer total = i % rows == 0? i / rows : i / rows + 1;
        List<Admin> admins = adminDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("records",i);
        map.put("page",page);
        map.put("total",total);
        map.put("rows",admins);
        return map;
    }

    @Override
    public Map insertAdmin(Admin admin) {
        Map map = new HashMap();
        String adminId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        admin.setId(adminId);
        adminDao.insert(admin);
        map.put("adminId",adminId);
        return map;
    }

    @Override
    public Map updateAdmin(Admin admin) {
        Map map = new HashMap();
        adminDao.updateByPrimaryKeySelective(admin);
        map.put("adminId",admin.getId());
        return map;
    }

    @Override
    public Map deleteAdmin(String[] id) {
        Map map = null;
        adminDao.deleteByIdList(Arrays.asList(id));
        return map;
    }
}
