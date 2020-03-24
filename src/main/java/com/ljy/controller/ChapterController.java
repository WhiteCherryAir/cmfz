package com.ljy.controller;

import com.ljy.entity.Chapter;
import com.ljy.service.ChapterService;
import com.ljy.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * (Chapter)表控制层
 *
 * @author makejava
 * @since 2019-12-28 16:24:08
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {
    /**
     * 服务对象
     */
    @Autowired
    private ChapterService chapterService;

    //分页
    @RequestMapping("ByPageChaper")
    public Map ByPageChaper(String albumId, Integer page, Integer rows) {
        return chapterService.ByPageChapter(albumId, page, rows);

    }

    //增删改
    @RequestMapping("editChapter")
    public Map edit(String oper, Chapter chapter, String[] id, String albumId) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            map = chapterService.insert(chapter,albumId);
        } else if (oper.equals("edit")) {
            map = chapterService.update(chapter);
        } else {
            chapterService.deleteById(id);
        }
        return map;
    }

    //章节上传
    @RequestMapping("uploadChapter")
    public Map upload(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        Map map = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        //判断文件夹如果不存在则创建
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //网络路径
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        //更新数据库信息
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        //计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        //计算音频时长
        //使用三方计算音频时长工具类，得到音频时长
        String[] split = http.split("/");
        String name = split[split.length - 1];
        //音频解析
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        //获取头部
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        //获取时长
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength / 60 + "分" + trackLength % 60 + "秒";
        chapter.setTime(time);
        chapterService.update(chapter);
        map.put("status", 200);
        return map;
    }
    //章节下载
    @RequestMapping("downloadChapter")
    public void download(String url, HttpServletResponse response,HttpSession session) throws IOException {
        String[] split = url.split("/");
        //获取文件真实位置
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        //获取源文件字节数组
        byte[] bytes = FileUtils.readFileToByteArray(new File(realPath+name));

        response.setContentType("application/force-download");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
    }
}