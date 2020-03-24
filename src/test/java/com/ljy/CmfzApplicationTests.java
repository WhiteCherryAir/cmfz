package com.ljy;

import com.alibaba.excel.EasyExcel;
import com.ljy.dao.AdminDao;
import com.ljy.dao.BannerDao;
import com.ljy.entity.Admin;
import com.ljy.entity.Banner;
import com.ljy.entity.BannerListener;
import io.goeasy.GoEasy;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    AdminDao adminDao;
    Admin admin;
    @Autowired
    private BannerDao bannerDao;

    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        System.out.println(admins);
    }

    @Test
    public void testSelectOne() {
        Admin admin = adminDao.selectOne(this.admin);
        System.out.println(admin);
    }

    @Test
    public void testPoiOne() {
        List<Banner> bannerList = bannerDao.selectAll();
        //创建文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作薄
        HSSFSheet sheet = workbook.createSheet();
        //创建一行
        HSSFRow row = sheet.createRow(0);

        String[] str = {"ID", "标题", "图片", "超链接", "创建时间", "描述", "状态"};
        //创建单元格
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            //设置单元格内容
            row.createCell(i).setCellValue(s);
        }
        //通过workbook对象获取样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //数据格式化处理
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //设置时间格式
        short format = dataFormat.getFormat("yyyy-MM-dd");
        cellStyle.setDataFormat(format);
        for (int i = 0; i < bannerList.size(); i++) {
            Banner banner = bannerList.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());
            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreate_date());

            row1.createCell(5).setCellValue(banner.getDescription());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例\\" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入
    @Test
    public void testPoiInput() {
        //创建excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作薄
        HSSFSheet sheet = workbook.createSheet();
        //创建一行
        HSSFRow row = sheet.createRow(0);
        //创建一个单元格
        HSSFCell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("zzzzzzz");
        //将excel文档做本地输出
        try {
            workbook.write(new FileOutputStream("G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void outPutBannerExcel() {
        String filename = "G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例\\" + new Date().getTime() + ".xls";
        List<Banner> bannerList = bannerDao.selectAll();
        EasyExcel.write(filename, Banner.class).sheet("轮播图信息").doWrite(bannerList);
    }

    @Test
    public void inPutBannerExcel() {
        String url = "G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例\\1577962580475.xls";
        EasyExcel.read(url, Banner.class, new BannerListener(bannerDao)).sheet().doRead();
    }

    @Test
    public void testGoeasy() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-1853cc09b905435db55c370114483489");
        goEasy.publish("cmfz", "Hello, GoEasy!");
    }

    @Test
    public void testShiro() {
        //获取安全管理器工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //用工厂获取安全管理器
        SecurityManager securityManager = iniSecurityManagerFactory.createInstance();
        //把安全管理器装配到当前shrio环境
        SecurityUtils.setSecurityManager(securityManager);
        //用shrio环境获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //前端输入用户名密码 封装令牌
        //UnkonwnAccountException:    未知的账户异常    相当于用户名不正确
        //IncorrectCrefdentialsException:   不正确的凭证异常  相当于密码不正确
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin", "admin");
        //登录
        subject.login(usernamePasswordToken);
        //判断用户认证是否通过subject.isAuthenticated()
        if (subject.isAuthenticated()) {
            System.out.println("helloWorld");
        }

    }

    @Test
    public void testShiro2() {
        // 1. 获取安全管理器工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2. 通过工厂获取安全管理器
        SecurityManager securityManager = iniSecurityManagerFactory.createInstance();
        // 3. 将安全管理器放置于当前Shiro环境中
        SecurityUtils.setSecurityManager(securityManager);
        // 4. shiro环境中获取主体对象
        Subject subject = SecurityUtils.getSubject();
        // 5. 通过前端输入用户名密码 封装令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin", "admin");
        // 6. 登陆
        // 注意 : shiro的登陆状况 由报错信息告知
        // IncorrectCredentialsException : 密码错误
        // UnknownAccountException : 未知账号异常
        subject.login(usernamePasswordToken);
        CredentialsMatcher c;
        // subject.isAuthenticated() 判断用户是否登陆
        if (subject.isAuthenticated()) {
            System.out.println("helloworld");
        }
        // 基于身份
        if (subject.hasRole("admin")) {
            System.out.println("我是管理员");
        }
        List<String> strings = Arrays.asList("admin", "vip", "superadmin");
        // subject.hasAllRoles(strings) 是否具有所有角色信息
        //System.out.println(subject.hasAllRoles(strings));
        // subject.hasRoles(strings) 依次对比
        //boolean[] booleans = subject.hasRoles(strings);
        // 基于资源
        //System.out.println(subject.isPermitted("admin:select"));
        //System.out.println(subject.isPermitted("admin:delete"));
        //System.out.println(subject.isPermitted("admin:update"));
        //System.out.println(subject.isPermitted("admin:insert"));
        /*
             基于代码的权限控制
             优点: 灵活
             缺点: 代码量大,开发速度慢
          */

    }

    @Test
    public void testMD5() {
        //加盐
        //String salt = "abcd";
        //md5加密算法
        long time = new Date().getTime();
        //                                    密码         盐值         散列值
        Md5Hash md5Hash = new Md5Hash("admin", "abcd", 1024);
        System.out.println(md5Hash);
        long time1 = new Date().getTime();
        Long time2 = time1 - time;
        System.out.println("执行时间" + time2);
    }
}