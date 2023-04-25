package org.koreait.restcontrollers;

import org.koreait.commons.rest.JSONResult;
import org.koreait.commons.rest.RestCommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.koreait.restcontrollers") // 범위설정
public class CommonRestController {

    @ExceptionHandler(RuntimeException.class) // 이셉션을 하나로 묶어줌??
    public ResponseEntity<JSONResult<Object>> errorHandler(Exception e) {
        JSONResult<Object> jsonResult = new JSONResult<>();
        jsonResult.setSuccess(false);
        jsonResult.setErrorMessage(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 기본값 500
        if(e instanceof RestCommonException){ // RestCommonException 인 경우는 상태코드 조회
            RestCommonException restE = (RestCommonException)e;
            status = restE.getStatus();
        }

        return ResponseEntity.status(status).body(jsonResult);
    }
}
