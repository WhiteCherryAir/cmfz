package com.ljy.controller;

import com.ljy.entity.Album;
import com.ljy.service.AlbumService;
import com.ljy.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * (Album)表控制层
 *
 * @author makejava
 * @since 2019-12-27 17:13:05
 */
@RestController
@RequestMapping("/album")
public class AlbumController {
    /**
     * 服务对象
     */
    @Autowired
    private AlbumService albumService;

    //分页
    @RequestMapping("queryByPageAlbum")
    public Map queryByPageAlbum(Integer page, Integer rows) {
        return albumService.queryByPageAlbum(page, rows);
    }

    //增删改封装
    @RequestMapping("editAlbum")
    public Map save(String oper, String[] id, Album album) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            map = albumService.insert(album);
        } else if (oper.equals("edit")) {
            map = albumService.update(album);
        } else {
            albumService.deleteById(id);
        }
        return map;
    }

    //上传
    @RequestMapping("uploadAlbum")
    public Map upload(MultipartFile cover, HttpSession session, String albumId, HttpServletRequest request){
        Map map = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/albumImg/");
        //判断文件夹是否存在
        File file = new File(realPath);
        //文件夹不存在则创建
        if(!file.exists()){
            file.mkdirs();
        }
        //网络路径
        String http = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        //更新数据库信息
        Album album = new Album();
        album.setId(albumId);
        album.setCover(http);
        albumService.update(album);
        map.put("status",200);
        return map;
    }
}