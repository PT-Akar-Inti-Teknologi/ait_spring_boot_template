package org.ait.project.guideline.example.blob.shared.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CannotUploadFileException extends RuntimeException {
  public CannotUploadFileException(String message) {
    super(message);
    log.error(String.format("Cannot Upload File: %s", message));
  }
}
