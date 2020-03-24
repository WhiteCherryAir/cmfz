package com.ljy.controller;

import com.alibaba.excel.EasyExcel;
import com.ljy.dao.BannerDao;
import com.ljy.entity.Banner;
import com.ljy.entity.BannerListener;
import com.ljy.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * (Banner)表控制层
 *
 * @author makejava
 * @since 2019-12-26 11:36:53
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    /**
     * 服务对象
     */
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    @RequestMapping("ByPageBanner")
    public Map ByPageBanner(Integer page, Integer rows) {
        return bannerService.queryAllByLimit(page, rows);
    }

    //增删改
    @RequestMapping("save")
    //参数oper,代表增删改的具体操作类型，名称 固定(jqgrid控制封装好的参数)
    public Map save(String oper, Banner banner, String[] id) {
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String bannerId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
            banner.setId(bannerId);
            bannerDao.insert(banner);
            hashMap.put("bannerId", bannerId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            bannerDao.updateByPrimaryKeySelective(banner);
            hashMap.put("bannerId", banner.getId());
            // 删除
        } else {
            bannerDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("upload")
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session) {
        Map map = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件夹是否存在
        File file1 = new File(realPath);
        if (!file1.exists()) {
            //没有则创建  mkdirs多级创建
            file1.mkdirs();
        }
        //解决文件重名
        long time = new Date().getTime();
        String originalFilename = time + url.getOriginalFilename();
        try {
            //上传文件
            url.transferTo(new File(realPath, originalFilename));
            //更新数据库信息
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl("/upload/img/" + originalFilename);
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("status", 200);
        return map;
    }

    //导出轮播图信息
    @RequestMapping("outPutBannerExcel")
    public void outPutBannerExcel() {

        String filename = "G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例\\"+new Date().getTime()+".xls";
        List<Banner> bannerList = bannerDao.selectAll();
        EasyExcel.write(filename,Banner.class).sheet("轮播图信息").doWrite(bannerList);
    }

    //导入轮播图信息
    @RequestMapping("inPutBannerExcel")
    public void inPutBannerExcel(MultipartFile inputBannerImg, HttpSession session){
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/ExcelBannerImg/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String originalFilename = inputBannerImg.getOriginalFilename();
        try {
            inputBannerImg.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "G:\\三阶段\\后期项目\\day7-poiEasyExcel(2)\\示例\\"+originalFilename;
        EasyExcel.read(url,Banner.class,new BannerListener(bannerDao)).sheet().doRead();
    }


    //Excel模板下载
    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        List<Banner> bannerList = bannerDao.selectAll();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode防止中文乱码,和easyexcel没有关系
        String fileName = URLEncoder.encode("Excel模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), Banner.class).sheet("模板").doWrite(bannerList);
    }
}