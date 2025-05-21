package com.tadaboh.datn.furniture.selling.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResponseError extends ResponseData {

    /**
     * Du lieu tra ve khi thuc thi api that bai cho http POST, GET, PUT, DELETE
     * @param status
     * @param message
     */

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
