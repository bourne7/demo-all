package com.example.demo.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Response {

    private String message;

    @Builder.Default
    private LocalDateTime localDateTime = LocalDateTime.now();

}
