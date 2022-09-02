package com.mf.dto;

import com.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseBean {
    private int id;
    private String account;
    private String name;
    private String password;
    private String email;
    private String token;
    private int role;
    private int status;
    private Date createTime;
    private Date updateTime;
}
