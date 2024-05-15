package org.ait.library.blob.shared.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CannotDownloadFileException extends RuntimeException {
  public CannotDownloadFileException(String message) {
    super(message);
    log.error(String.format("Cannot Download File: %s", message));
  }
}
