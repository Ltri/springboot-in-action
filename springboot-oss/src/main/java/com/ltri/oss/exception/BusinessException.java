package com.ltri.oss.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Exception e) {
        this.code = 500;
        this.message = e.getMessage();
    }

    public BusinessException(String message) {
        this.code = 500;
        this.message = message;
    }
}
