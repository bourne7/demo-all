package com.pbr.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseUserAndTimeDTO {

    private String createUser;

    private String updateUser;

    private Date createTime;

    private Date updateTime;
}
