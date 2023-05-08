package com.pbr.mq;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MyEvent {

    private String code;

    private Date createTime;

}
