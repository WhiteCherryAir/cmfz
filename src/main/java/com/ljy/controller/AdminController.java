package com.ljy.controller;

import com.ljy.entity.Admin;
import com.ljy.service.AdminService;
import com.ljy.util.CreateValidateCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String username, HttpSession session, String password, String clientCode) {
        String serverCode = session.getAttribute("ServerCode").toString();
        Subject subject = SecurityUtils.getSubject();
        //构建需要返回的map集合
        HashMap hashMap = new HashMap();
        //构建查询条件
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        System.out.println(serverCode + "-======================********-----------------");
        if (clientCode.equals(serverCode)) {
            try {
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException exception) {
                hashMap.put("status", 400);
                hashMap.put("msg", "用户不存在");
                return hashMap;
            } catch (IncorrectCredentialsException exception) {
                hashMap.put("status", 400);
                hashMap.put("msg", "密码错误");
                return hashMap;
            }
            hashMap.put("status", 200);

        } else {
            hashMap.put("status", 400);
            hashMap.put("msg", "验证码错误");
        }
        return hashMap;
    }

    //验证码
    @RequestMapping("/verify")
    public void verify(HttpSession session, HttpServletResponse response) throws IOException {
        //使用验证码插件
        CreateValidateCode vcode = new CreateValidateCode();
        //获取随机验证码
        String code = vcode.getCode();
        //输出图片到client
        vcode.write(response.getOutputStream());
        //获取session
        session.setAttribute("ServerCode", code);
    }

    //退出登录
    @RequestMapping("/quit")
    //HttpSession session
    public String quit() {
        //session.invalidate();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/jsp/login.jsp";
    }

    //分页
    @RequestMapping("byPageAdminController")
    public Map byPageAdminController(Integer page, Integer rows) {
        return adminService.queryByPageAdmin(page, rows);
    }

    //增删改
    @RequestMapping("editAdmin")
    public Map editAdmin(String oper, String[] id, Admin admin) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            map = adminService.insertAdmin(admin);
        } else if (oper.equals("edit")) {
            map = adminService.updateAdmin(admin);
        } else {
            adminService.deleteAdmin(id);
        }
        return map;
    }
}
