package org.ait.project.template.configuration.exception;

import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.template.shared.enums.ResponseEnum;
import org.ait.project.template.shared.template.ErrorDetail;
import org.ait.project.template.shared.template.ResponseError;
import org.ait.project.template.shared.template.ResponseTemplate;
import org.ait.project.template.shared.utils.ResponseHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  private final ResponseHelper responseHelper;

  @ExceptionHandler(ModuleException.class)
  public <T> ResponseEntity<ResponseTemplate<T>> handleException(ModuleException exception) {
    return responseHelper.createResponseError(exception.getResponseEnum(), null);
  }

  @ExceptionHandler(Exception.class)
  public <T> ResponseEntity<ResponseTemplate<T>> handleException(Exception ex,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response) {
    Arrays.stream(ex.getStackTrace()).limit(5).forEach(logger::error);
    logger.error(ex.getMessage());
    return responseHelper.createResponseError(ResponseEnum.INTERNAL_SERVER_ERROR, null);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ResponseError responseError = new ResponseError(new ArrayList<>());

    ex.getFieldErrors().forEach(
        fieldError ->
            responseError.getErrorDetailList()
                .add(new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
    );

    return ResponseEntity
        .badRequest()
        .body(
            responseHelper.createResponseErrorTemplate(ResponseEnum.INVALID_PARAM, responseError));
  }
}
