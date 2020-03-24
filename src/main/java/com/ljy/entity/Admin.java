package com.ljy.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin implements Serializable {
    @Id
    private String id;

    private String username;
    private String password;
    private String salt;
    private List<Role> roles;
}
