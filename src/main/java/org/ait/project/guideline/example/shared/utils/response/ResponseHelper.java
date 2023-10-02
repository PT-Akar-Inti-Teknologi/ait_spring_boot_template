package org.ait.project.guideline.example.shared.utils.response;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseError;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.transform.ResponseTemplateTransform;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseHelper {

  private final ResponseMessageHelper responseMessageHelper;

  private final ResponseTemplateTransform responseTransform;

  /**
   * a utility function that is used to generate detailed responses based on the AIT Standard
   * @param responseEnum Enum Response
   * @param body Body Return
   * @param <T> Generic Type, for class body return
   * @return ResponseEntity contain Template Response based on AIT Standard
   */
  public <T> ResponseEntity<ResponseTemplate<ResponseDetail<T>>> createResponseDetail(
      ResponseEnum responseEnum, T body) {
    return ResponseEntity.status(responseEnum.getHttpStatus())
        .body(
            responseTransform.templateDetail(responseMessageHelper.getResponseSchema(responseEnum), body)
        );
  }

  public <T> ResponseEntity<ResponseTemplate<T>> createResponseError(
      ResponseEnum responseEnum, T body) {
    return ResponseEntity.status(responseEnum.getHttpStatus())
        .body(
            responseTransform.templateError(responseMessageHelper.getResponseSchema(responseEnum), body)
        );
  }

  public <T> ResponseEntity<ResponseTemplate<ResponseCollection<T>>> createResponseCollection(
      ResponseEnum responseEnum, Page page,
      List<T> contents) {
    return ResponseEntity.status(responseEnum.getHttpStatus())
        .body(
            responseTransform.templateCollection(responseMessageHelper.getResponseSchema(responseEnum), page,contents)
        );
  }

  public Object createResponseErrorTemplate(ResponseEnum invalidParam, ResponseError responseError) {
    return responseTransform.templateError(responseMessageHelper.getResponseSchema(invalidParam), responseError);
  }
}
