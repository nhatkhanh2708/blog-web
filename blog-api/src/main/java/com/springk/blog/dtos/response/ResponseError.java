package com.springk.blog.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {
    private int status;
    private String message;
    private long timestamp;
    public ResponseError(int status, String message){
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
