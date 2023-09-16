package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    // boolean, message, data 형태로 결과를 response 하는 클래스
    private boolean result;
    private String message;
    private D data;

    // 5. 13분
    public static <D> ResponseDto<D> setSuccess(String message, D data) {
        // return new ResponseDto(true, message, data);
        // set을 썼기 때문에 아래로 변경가능
        return ResponseDto.set(true, message, data);
    }
    // 5. 14분
    public static <D> ResponseDto<D> setFailed(String message) {
        return ResponseDto.set(false, message, null);
    }
}
