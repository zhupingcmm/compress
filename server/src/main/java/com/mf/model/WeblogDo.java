package com.mf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeblogDo {
    private long id;
    private String url;
    private String ip;
    private String classInfo;
    private String method;
    private String methodName;
    private String browser;
    private String browserVersion;
    private String os;
    private long takeTime;
    private Date createTime;
}
