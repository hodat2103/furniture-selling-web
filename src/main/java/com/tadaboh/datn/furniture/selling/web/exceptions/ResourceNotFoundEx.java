package com.tadaboh.datn.furniture.selling.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundEx extends RuntimeException{
    public ResourceNotFoundEx(String s) {
        super(s);
    }
}
