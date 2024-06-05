package org.ait.project.guideline.example.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ErrorDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseError;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    private final ResponseHelper responseHelper;

    @ExceptionHandler(ModuleException.class)
    public <T> ResponseEntity<ResponseTemplate<T>> handleException(ModuleException exception) {
        return responseHelper.createResponseError(exception.getResponseEnum(), null);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ResponseTemplate<T>> handleException(Exception ex,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response) {
        Arrays.stream(ex.getStackTrace()).limit(20).forEach(stackTraceElement -> {
            log.error(stackTraceElement.toString());
        });
        log.error(ex.getMessage());
        return responseHelper.createResponseError(ResponseEnum.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseTemplate<ResponseError>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                                        HttpServletRequest request,
                                                                                        HttpServletResponse response) {

        ResponseError responseError = new ResponseError(new ArrayList<>());

        ex.getFieldErrors().forEach(
                fieldError ->
                        responseError.getErrorDetailList()
                                .add(new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
        );

        Arrays.stream(ex.getStackTrace()).limit(5).forEach(stackTraceElement -> {
            log.error(stackTraceElement.toString());
        });
        log.error(ex.getMessage());
        return responseHelper.createResponseError(ResponseEnum.INVALID_PARAM, responseError);
    }
}
