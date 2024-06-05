package org.ait.project.guideline.example.shared.utils.response;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.*;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseHelper {

    private final ResponseMessageHelper responseMessageHelper;

    /**
     * a utility function that is used to generate detailed responses based on the AIT Standard
     *
     * @param responseEnum Enum Response
     * @param body         Body Return
     * @param <T>          Generic Type, for class body return
     * @return ResponseEntity contain Template Response based on AIT Standard
     */
    public <T> ResponseEntity<ResponseTemplate<ResponseDetail<T>>> createResponseDetail(
            ResponseEnum responseEnum, T body) {
        return ResponseEntity.status(responseEnum.getHttpStatus())
                .body(
                        new ResponseTemplate<>(responseMessageHelper.getResponseSchema(responseEnum), new ResponseDetail<>(body))
                );
    }

    public <T> ResponseEntity<ResponseTemplate<T>> createResponseError(
            ResponseEnum responseEnum, T body) {
        return ResponseEntity.status(responseEnum.getHttpStatus())
                .body(
                        new ResponseTemplate<>(responseMessageHelper.getResponseSchema(responseEnum), body)
                );
    }

    public <P, T> ResponseEntity<ResponseTemplate<ResponseCollection<T>>> createResponseCollection(
            ResponseEnum responseEnum, Page<P> page,
            List<T> contents) {
        return ResponseEntity.status(responseEnum.getHttpStatus())
                .body(
                        new ResponseTemplate<>(responseMessageHelper.getResponseSchema(responseEnum), createCollection(page, contents))
                );
    }

    private <P, T> ResponseCollection<T> createCollection(Page<P> page, List<T> contents) {
        return new ResponseCollection<>(
                new ResponseCollectionContent<>(Optional.ofNullable(page).map(
                        pageableData -> new PaginationConfig(page.getNumber(),
                                page.getSize(),
                                page.getTotalElements(),
                                page.getTotalPages())).orElse(null), contents));
    }

    public ResponseEntity<Resource> createResponseOctet(String fileName, AbstractResource resource) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        MediaType mediaType = getMediaType(fileName);
        ResponseEntity.BodyBuilder body= ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType);

        if(resource instanceof ByteArrayResource arrayResource) {
            body.contentLength(arrayResource.contentLength());
        }
        return body.body(resource);
    }

    private MediaType getMediaType(String fileName) {
        String type = fileName.split("\\.")[1];
        switch (type) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public Object createResponseErrorTemplate(ResponseEnum invalidParam, ResponseError responseError) {
        return new ResponseTemplate<>(responseMessageHelper.getResponseSchema(invalidParam), responseError);
    }
}
