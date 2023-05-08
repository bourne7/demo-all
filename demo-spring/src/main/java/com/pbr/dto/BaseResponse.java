package com.pbr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pbr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    private String bizId;

    private String code;

    private String message;

    private String requestId;

}
