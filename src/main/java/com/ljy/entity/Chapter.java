package com.ljy.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Chapter)实体类
 *
 * @author makejava
 * @since 2019-12-28 16:24:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter implements Serializable {
    @Id
    private String id;
    
    private String title;
    
    private String url;
    
    private Double size;
    
    private String time;
    @JSONField(format = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    private String albumId;
}