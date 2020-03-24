package com.ljy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Banner)实体类
 *
 * @author makejava
 * @since 2019-12-26 11:36:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner implements Serializable {
    //声明表头信息
    @ExcelProperty(value = "ID")
    @Id
    private String id;
    @ExcelProperty(value = "标题")
    private String title;
    @ExcelProperty(value = "图片")
    private String url;
    @ExcelProperty(value = "超链接")
    private String href;
    @ExcelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date create_date;
    @ExcelProperty(value = "描述")
    private String description;
    @ExcelProperty(value = "状态")
    private String status;

}