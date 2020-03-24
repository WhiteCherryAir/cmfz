package com.ljy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2020-01-13 19:35:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role implements Serializable {
    @Id
    private String id;
    
    private String roleName;

    private List<Resource> resources;

}