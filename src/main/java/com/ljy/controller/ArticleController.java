package com.ljy.controller;

import com.ljy.entity.Article;
import com.ljy.service.ArticleService;
import com.ljy.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2019-12-30 20:13:42
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleService articleService;

    @RequestMapping("articleByPage")
    public Map articleByPage(Integer page,Integer rows){
        return articleService.ByPageArticle(page,rows);
    }
    //增删改封装
    @RequestMapping("articleEdit")
    public Map articleEdit(String oper, Article article, String[] id){
        Map map = new HashMap();
        if(oper.equals("add")){
           map =  articleService.insert(article);
        }else if(oper.equals("edit")){
            map = articleService.update(article);
        }else{
            articleService.deleteById(id);
        }
        return map;
    }

    @RequestMapping("insertArticle")
    public String insertArticle(Article article,MultipartFile inputfile,String guruId,HttpSession session,HttpServletRequest request){
        if(article.getId()==null || article.getId().equals("")){
            String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            //网络路径
            String http = HttpUtil.getHttp(inputfile, request, "/upload/articleImg/");
            article.setGuruId(guruId);
            article.setImg(http);
            articleService.insert(article);
        }else{
            //update
            articleService.update(article);
        }
      return "ok";
    }

    @RequestMapping("updateArticle")
    public Map updateArticle(Article article){
        return articleService.update(article);
    }

    @RequestMapping("deleteArticle")
    public Map updateArticle(String[] id){
        return articleService.deleteById(id);
    }

    //上传图片
    //富文本编辑器
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        //方法返回信息 error状态码 成功 0 失败 1
        try {
            //网络路径
            String http = HttpUtil.getHttp(imgFile, request, "/upload/articleImg/");
            Article article = new Article();
            article.setImg(http);
            articleService.update(article);
            hashMap.put("error",0);
            hashMap.put("url",http);

        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("error",1);
        }
        return hashMap;
    }

    //展示图片
    @RequestMapping("showImg")
    public Map showImg(HttpServletRequest request,HttpSession session){
        Map map = new HashMap();
        map.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        map.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            Map fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            //将字符串拆分获取时间戳
            String time = name.split("_")[0];
            //指定时间样式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //获取指定样式字符串
            //将string类型转为long  long.valueof(str)
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        map.put("file_list",arrayList);
        return map;
    }
}