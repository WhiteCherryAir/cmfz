package com.ljy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Resource)实体类
 *
 * @author makejava
 * @since 2020-01-13 19:37:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resource implements Serializable {

    private String id;
    
    private String resourceName;


}