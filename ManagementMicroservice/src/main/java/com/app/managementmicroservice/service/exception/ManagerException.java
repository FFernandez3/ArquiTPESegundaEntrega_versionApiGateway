package com.app.managementmicroservice.service.exception;

import lombok.Getter;

@Getter
public class ManagerException extends RuntimeException {

    private final EnumManagerException code;
    private final String message;

    public ManagerException(EnumManagerException code, String message ){
        this.code = code;
        this.message = message;
    }
}
